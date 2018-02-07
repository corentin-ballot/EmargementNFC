package ballot.corentin.emargementnfc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brentot on 25/01/2018.
 */

public class PresenceDBHelper extends SQLiteOpenHelper {

    //info général DB
    public final static String LOG_TAG = "DB log";
    public final static String DB_NAME = "PresenceDB";
    public final static int VERSION = 1;

    //info table ETUDIANT
    public final static String ETUDIANT_TABLE_NAME ="etudiant";
    public final static String ETUDIANT_ID = "etudiant_id";
    public final static String ETUDIANT_NAME = "etudiant_name";
    public final static String ETUDIANT_SURNAME = "etudiant_surname";
    public final static String ETUDIANT_NUM_CARTE = "etudiant_num_carte";

    //info table EXAMEN
    public final static String EXAMEN_TABLE_NAME = "examen";
    public final static String EXAMEN_ID = "examen_id";
    public final static String EXAMEN_NAME = "examen_name";
    public final static String EXAMEN_DATE = "examen_date";
    public final static String EXAMEN_TERMINER = "examen_terminer";

    //info table PRESENCE
    public final static String PRESENCE_TABLE_NAME = "presence";
    public final static String PRESENCE_ID = "presence_id";
    public final static String PRESENCE_EXAMEN_ID = "presence_examen_id";
    public final static String PRESENCE_ETUDIANT_ID = "presence_etudiant_id";
    public final static String PRESENCE_DATE = "presence_date";


    Context context;

    public PresenceDBHelper(Context context){
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase sqldb){

        //création de la table ETUDIANT
        sqldb.execSQL("CREATE TABLE " + ETUDIANT_TABLE_NAME + "("
                + ETUDIANT_ID + " INTEGER PRIMARY KEY , "
                + ETUDIANT_NAME + " TEXT NOT NULL, "
                + ETUDIANT_SURNAME + " TEXT NOT NULL, "
                + ETUDIANT_NUM_CARTE + " TEXT NOT NULL, "
                + "CONSTRAINT UC_Carte_ID UNIQUE (ETUDIANT_NUM_CARTE));"
        );

        //création de la table EXAMEN
        sqldb.execSQL("CREATE TABLE " + EXAMEN_TABLE_NAME + "("
                + EXAMEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EXAMEN_NAME + " TEXT NOT NULL,"
                + EXAMEN_DATE + " TEXT NOT NULL,"
                + EXAMEN_TERMINER + " INTEGER NOT NULL);"
        );

        //création de la table PRESENCE
        sqldb.execSQL("CREATE TABLE " + PRESENCE_TABLE_NAME + "("
                + PRESENCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRESENCE_ETUDIANT_ID + " INTEGER NOT NULL, "
                + PRESENCE_EXAMEN_ID + " INTEGER NOT NULL,"
                + PRESENCE_DATE + " INTEGER NOT NULL,"
                + "CONSTRAINT UC_Presence UNIQUE (" + PRESENCE_ETUDIANT_ID + "," + PRESENCE_EXAMEN_ID + "));"
        );
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

    }

    public void deleteDatabase(){
        context.deleteDatabase(DB_NAME);
    }


}
