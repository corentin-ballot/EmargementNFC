package ballot.corentin.emargementnfc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EmargementActivity extends AppCompatActivity {

    BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emargement);

        br = new Receiver();
        IntentFilter filter = new IntentFilter("ballot.corentin.emargementnfc.NFC_TAG_ID");
        this.registerReceiver(br, filter);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(br);
        super.onStop();
    }

    public class Receiver extends BroadcastReceiver {
        private static final String TAG = "MyBroadcastReceiver";
        @Override
        public void onReceive(Context context, Intent intent) {
            /* TODO: ajouter l'étudiant à la liste en base de données */

            // String id = intent.getExtras().getString("id")
        }
    }
}
