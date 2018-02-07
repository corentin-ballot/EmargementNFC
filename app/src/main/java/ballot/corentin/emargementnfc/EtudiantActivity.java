package ballot.corentin.emargementnfc;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EtudiantActivity extends AppCompatActivity {

    EditText et_id;
    EditText et_nom;
    EditText et_prenom;
    EditText et_carte_id;

    private SQLiteDatabase sqlitedb = null;
    private PresenceDBHelper preDB = null;

    private BroadcastReceiver br;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant);

        et_id = findViewById(R.id.etudiant_id);
        et_nom = findViewById(R.id.etudiant_nom);
        et_prenom = findViewById(R.id.etudiant_prenom);
        et_carte_id = findViewById(R.id.etudiant_carte_id);

        preDB = new PresenceDBHelper(this);
        sqlitedb = preDB.getWritableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        br = new Receiver();

        id = getIntent().getStringExtra("ETUDIANT_DATA").split(" | ")[0];

        Cursor cursor = sqlitedb.rawQuery("select * from " + PresenceDBHelper.ETUDIANT_TABLE_NAME + " where " + PresenceDBHelper.ETUDIANT_ID + " = ?", new String[] { id });

        et_id.setText(id);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            et_nom.setText(cursor.getString(cursor.getColumnIndex(PresenceDBHelper.ETUDIANT_SURNAME)));
            et_prenom.setText(cursor.getString(cursor.getColumnIndex(PresenceDBHelper.ETUDIANT_NAME)));
            et_carte_id.setText(cursor.getString(cursor.getColumnIndex(PresenceDBHelper.ETUDIANT_NUM_CARTE)));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public class Receiver extends BroadcastReceiver {
        private static final String TAG = "MyBroadcastReceiver";
        @Override
        public void onReceive(Context context, Intent intent) {
            et_carte_id.setText(intent.getExtras().getString("id"));
        }
    }

    public  void update(View view) {
        ContentValues cv = new ContentValues();
        cv.put(PresenceDBHelper.ETUDIANT_SURNAME,et_nom.getText().toString()); //These Fields should be your String values of actual column names
        cv.put(PresenceDBHelper.ETUDIANT_NAME,et_prenom.getText().toString());
        cv.put(PresenceDBHelper.ETUDIANT_NUM_CARTE,et_carte_id.getText().toString());

        sqlitedb.update(PresenceDBHelper.ETUDIANT_TABLE_NAME, cv, PresenceDBHelper.ETUDIANT_ID + "="+id, null);
        this.finish();
    }

    public void delete(View view) {
        sqlitedb.delete(PresenceDBHelper.ETUDIANT_TABLE_NAME, PresenceDBHelper.ETUDIANT_ID + "=" + id, null);
        this.finish();
    }
}
