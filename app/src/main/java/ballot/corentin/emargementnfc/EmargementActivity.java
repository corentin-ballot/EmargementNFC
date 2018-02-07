package ballot.corentin.emargementnfc;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EmargementActivity extends AppCompatActivity {

    ListView lv_list_emargement;
    TextView et_title;

    BroadcastReceiver br;

    private SQLiteDatabase sqlitedb = null;
    private PresenceDBHelper preDB = null;

    SimpleDateFormat time = new SimpleDateFormat("kk:mm");

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emargement);

        lv_list_emargement = (ListView) findViewById(R.id.list_emargement);
        et_title = (TextView) findViewById(R.id.emaregment_title);

        preDB = new PresenceDBHelper(this);
        sqlitedb = preDB.getWritableDatabase();

        br = new Receiver();
        IntentFilter filter = new IntentFilter("ballot.corentin.emargementnfc.NFC_TAG_ID");
        this.registerReceiver(br, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        id = getIntent().getStringExtra("EMARGEMENT_DATA").split(". ")[0];
        et_title.setText(getIntent().getStringExtra("EMARGEMENT_DATA").split("\\. ")[1]);

        updateListView();
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
            String etudiant_carte_id = intent.getExtras().getString("id");

            ContentValues cv = new ContentValues();
            cv.put(PresenceDBHelper.PRESENCE_EXAMEN_ID, id);
            cv.put(PresenceDBHelper.PRESENCE_ETUDIANT_ID, etudiant_carte_id);
            cv.put(PresenceDBHelper.PRESENCE_DATE, new Date().getTime());
            sqlitedb.insert(PresenceDBHelper.PRESENCE_TABLE_NAME, null, cv);
            
            updateListView();
        }
    }

    private void updateListView() {
        Cursor cursor = sqlitedb.rawQuery("SELECT * FROM " + PresenceDBHelper.PRESENCE_TABLE_NAME +
                        " INNER JOIN " + PresenceDBHelper.ETUDIANT_TABLE_NAME +
                        " ON " + PresenceDBHelper.PRESENCE_ETUDIANT_ID + " = " + PresenceDBHelper.ETUDIANT_NUM_CARTE +
                        " WHERE " + PresenceDBHelper.PRESENCE_EXAMEN_ID + " = '" + id + "'"
                , null);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, answerCursorToStrings(cursor));

        lv_list_emargement.setAdapter(adapter);
        lv_list_emargement.setBackgroundColor(Color.rgb(0, 153, 51));
    }

    public ArrayList<String> answerCursorToStrings(Cursor answer){
        answer.moveToFirst();

        ArrayList<String> answers = new ArrayList<>();
        String answerString = "";

        while(!answer.isAfterLast()){
            for (int c=0; c<answer.getColumnCount(); c++){
                answerString = time.format(new Date(Long.parseLong(answer.getString(answer.getColumnIndex(PresenceDBHelper.PRESENCE_DATE))))).toString() + " | ";

                answerString+= answer.getString(answer.getColumnIndex(PresenceDBHelper.ETUDIANT_ID)) + " | ";
                answerString+= answer.getString(answer.getColumnIndex(PresenceDBHelper.ETUDIANT_NAME)) + " ";
                answerString+= answer.getString(answer.getColumnIndex(PresenceDBHelper.ETUDIANT_SURNAME));
            }
            answer.moveToNext();
            answers.add(answerString);
        }
        return answers;
    }

    public void export(View view) {
        try {
            String examName = et_title.getText().toString().split(" ")[0];
            String fileName = "export-" + examName + "-" + new Date().getTime() + ".txt";

            File mediaDir = new File("/sdcard/download/media");
            if (!mediaDir.exists()){
                mediaDir.mkdir();
            }

            File file = new File("/sdcard/download/media/" + fileName);
            file.createNewFile();

            FileOutputStream outputStream = new FileOutputStream(file);
            PrintStream printStream = new PrintStream(outputStream);
            printStream.println(et_title.getText().toString());

            Cursor cursor = sqlitedb.rawQuery("SELECT * FROM " + PresenceDBHelper.PRESENCE_TABLE_NAME +
                            " INNER JOIN " + PresenceDBHelper.ETUDIANT_TABLE_NAME +
                            " ON " + PresenceDBHelper.PRESENCE_ETUDIANT_ID + " = " + PresenceDBHelper.ETUDIANT_NUM_CARTE +
                            " WHERE " + PresenceDBHelper.PRESENCE_EXAMEN_ID + " = '" + id + "'"
                    , null);

            ArrayList<String> data = answerCursorToStrings(cursor);

            for (int i = 0; i < data.size(); i++)
                printStream.println(data.get(i));

            printStream.close();
            outputStream.close();

            Toast.makeText(this, "File save in " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(View view) {
        sqlitedb.delete(PresenceDBHelper.PRESENCE_TABLE_NAME, PresenceDBHelper.PRESENCE_EXAMEN_ID + "=" + id, null);
        sqlitedb.delete(PresenceDBHelper.EXAMEN_TABLE_NAME, PresenceDBHelper.EXAMEN_ID + "=" + id, null);
        this.finish();
    }
}
