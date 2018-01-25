package ballot.corentin.emargementnfc;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddEmargementActivity extends AppCompatActivity {

    private SQLiteDatabase sqlitedb = null;
    private PresenceDBHelper preDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emargement);

        preDB = new PresenceDBHelper(this);
        sqlitedb = preDB.getWritableDatabase();
    }

    public boolean addNewExamen(String name, String date){
        ContentValues cv = new ContentValues();
        cv.put(PresenceDBHelper.EXAMEN_NAME, name);
        cv.put(PresenceDBHelper.EXAMEN_DATE, date);
        cv.put(PresenceDBHelper.EXAMEN_TERMINER, 0);
        sqlitedb.insert(PresenceDBHelper.EXAMEN_TABLE_NAME, null, cv);
        return true;
    }

    public void add(View view) {
        try {
            String nom = ((TextView) findViewById(R.id.intitule)).getText().toString();
            String date = ((TextView) findViewById(R.id.exam_date)).getText().toString();

            // check data validity
            if(nom.length() <= 0 || date.length() <= 0)
                throw new Exception();

            addNewExamen(nom, date);

            Toast.makeText(this, "Examen ajouté", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
        }
    }
}
