package ballot.corentin.emargementnfc;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private TextView tv_tag_id;
    private BroadcastReceiver br;

    private SQLiteDatabase sqlitedb = null;
    private PresenceDBHelper preDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tv_tag_id = (TextView) findViewById(R.id.register_tag_id);

        preDB = new PresenceDBHelper(this);
        sqlitedb = preDB.getWritableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();

        br = new Receiver();
        IntentFilter filter = new IntentFilter("ballot.corentin.emargementnfc.NFC_TAG_ID");
        this.registerReceiver(br, filter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(br);
        super.onPause();
    }

    public void addnext(View view) {
        add(view);

        Intent monIntent = new Intent(this, RegisterActivity.class);
        startActivity(monIntent);
    }

    public void add(View view) {
        try {
            int num_etu = Integer.parseInt(((TextView) findViewById(R.id.num_etu)).getText().toString());
            String nom = ((TextView) findViewById(R.id.nom)).getText().toString();
            String prenom = ((TextView) findViewById(R.id.prenom)).getText().toString();
            String carte_id = ((TextView) findViewById(R.id.register_tag_id)).getText().toString();

            // check data validity
            if(nom.length() <= 0 || prenom.length() <= 0 || carte_id.length() != 20)
                throw new Exception();

            addNewEtudiant(num_etu, nom, prenom, carte_id);

            Toast.makeText(this, "Etudiant ajouté", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
        }
    }

    public class Receiver extends BroadcastReceiver {
        private static final String TAG = "MyBroadcastReceiver";
        @Override
        public void onReceive(Context context, Intent intent) {
            tv_tag_id.setText(intent.getExtras().getString("id"));
        }
    }

    public boolean addNewEtudiant(int numEtud, String name, String surname, String carte){
        ContentValues cv = new ContentValues();
        cv.put(PresenceDBHelper.ETUDIANT_NAME, name);
        cv.put(PresenceDBHelper.ETUDIANT_SURNAME, surname);
        cv.put(PresenceDBHelper.ETUDIANT_NUM_CARTE, carte);
        cv.put(PresenceDBHelper.ETUDIANT_ID, numEtud);
        sqlitedb.insert(PresenceDBHelper.ETUDIANT_TABLE_NAME, null, cv);
        return true;
    }
}
