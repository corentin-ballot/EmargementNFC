package ballot.corentin.emargementnfc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

    TextView tv_tag_id;
    BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tv_tag_id = (TextView) findViewById(R.id.register_tag_id);

        br = new Receiver();
        IntentFilter filter = new IntentFilter("ballot.corentin.emargementnfc.NFC_TAG_ID");
        this.registerReceiver(br, filter);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(br);
        super.onStop();
    }

    public void addnext(View view) {
        add(view);

        Intent monIntent = new Intent(this, RegisterActivity.class);
        startActivity(monIntent);
    }

    public void add(View view) {
        /* TODO: ajouter l'étudiant en base de données */
        finish();
    }

    public class Receiver extends BroadcastReceiver {
        private static final String TAG = "MyBroadcastReceiver";
        @Override
        public void onReceive(Context context, Intent intent) {
            tv_tag_id.setText(intent.getExtras().getString("id"));
        }
    }
}
