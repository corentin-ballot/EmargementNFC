package ballot.corentin.emargementnfc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static ballot.corentin.emargementnfc.PresenceDBHelper.ETUDIANT_ID;
import static ballot.corentin.emargementnfc.PresenceDBHelper.ETUDIANT_NAME;
import static ballot.corentin.emargementnfc.PresenceDBHelper.ETUDIANT_SURNAME;
import static ballot.corentin.emargementnfc.PresenceDBHelper.EXAMEN_DATE;
import static ballot.corentin.emargementnfc.PresenceDBHelper.EXAMEN_ID;
import static ballot.corentin.emargementnfc.PresenceDBHelper.EXAMEN_NAME;

public class EtudiantsActivity extends AppCompatActivity {

    ListView lv_list_etudiant;

    private SQLiteDatabase sqlitedb = null;
    private PresenceDBHelper preDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiants);

        lv_list_etudiant = (ListView) findViewById(R.id.list_etudiant);

        preDB = new PresenceDBHelper(this);
        sqlitedb = preDB.getWritableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] paramSelect = {PresenceDBHelper.ETUDIANT_ID, PresenceDBHelper.ETUDIANT_NAME, PresenceDBHelper.ETUDIANT_SURNAME};
        Cursor answer = sqlitedb.query(PresenceDBHelper.ETUDIANT_TABLE_NAME, paramSelect, null, null, null, null, null);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, answerCursorToStrings(answer));

        //adapter.add(id);
        lv_list_etudiant.setAdapter(adapter);

        lv_list_etudiant.setBackgroundColor(Color.BLACK);
    }

    public void add(View view) {
        Intent monIntent = new Intent(this, RegisterActivity.class);
        startActivity(monIntent);
    }

    public ArrayList<String> answerCursorToStrings(Cursor answer){
        answer.moveToFirst();

        ArrayList<String> answers = new ArrayList<>();
        String answerString = "";

        while(!answer.isAfterLast()){
            for (int c=0; c<answer.getColumnCount(); c++){
                answerString = answer.getString(answer.getColumnIndex(ETUDIANT_ID)) + " | ";
                answerString+= answer.getString(answer.getColumnIndex(ETUDIANT_NAME));
                answerString+= " "  + answer.getString(answer.getColumnIndex(ETUDIANT_SURNAME));
            }
            answer.moveToNext();
            answers.add(answerString);
        }
        return answers;
    }
}
