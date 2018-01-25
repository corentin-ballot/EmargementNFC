package ballot.corentin.emargementnfc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static ballot.corentin.emargementnfc.PresenceDBHelper.EXAMEN_DATE;
import static ballot.corentin.emargementnfc.PresenceDBHelper.EXAMEN_ID;
import static ballot.corentin.emargementnfc.PresenceDBHelper.EXAMEN_NAME;

public class EmargementsActivity extends AppCompatActivity {

    ListView lv_list_emargement;
    BroadcastReceiver br;

    private SQLiteDatabase sqlitedb = null;
    private PresenceDBHelper preDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emargements);

        lv_list_emargement = (ListView) findViewById(R.id.list_emargement);

        preDB = new PresenceDBHelper(this);
        sqlitedb = preDB.getWritableDatabase();

        br = new Receiver();
        IntentFilter filter = new IntentFilter("ballot.corentin.emargementnfc.NFC_TAG_ID");
        this.registerReceiver(br, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] paramSelect = {PresenceDBHelper.EXAMEN_ID, PresenceDBHelper.EXAMEN_NAME, PresenceDBHelper.EXAMEN_DATE};
        Cursor answer = sqlitedb.query(PresenceDBHelper.EXAMEN_TABLE_NAME, paramSelect, null, null, null, null, null);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, answerCursorToStrings(answer));

        //adapter.add(id);
        lv_list_emargement.setAdapter(adapter);

        lv_list_emargement.setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void onStop() {
        try {
            unregisterReceiver(br);
        } catch (Exception e){}
        super.onStop();
    }

    public void add(View view) {
        Intent monIntent = new Intent(this, AddEmargementActivity.class);
        startActivity(monIntent);
    }

    public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            /* TODO: ajouter l'étudiant à la ListView et en base de données */
            String id = intent.getExtras().getString("id");

        }
    }

    public ArrayList<String> answerCursorToStrings(Cursor answer){
        answer.moveToFirst();

        ArrayList<String> answers = new ArrayList<>();
        String answerString = "";

        while(!answer.isAfterLast()){
            for (int c=0; c<answer.getColumnCount(); c++){
                answerString = answer.getString(answer.getColumnIndex(EXAMEN_ID)) + ". ";
                answerString+= answer.getString(answer.getColumnIndex(EXAMEN_NAME));
                answerString+= " ("  + answer.getString(answer.getColumnIndex(EXAMEN_DATE)) + ")";
            }
            answer.moveToNext();
            answers.add(answerString);
        }
        return answers;
    }
}
