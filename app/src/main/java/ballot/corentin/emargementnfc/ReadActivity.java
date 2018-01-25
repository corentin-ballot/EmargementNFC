package ballot.corentin.emargementnfc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ReadActivity extends NfcBaseActivity {

    final protected static char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_read);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] technology = tag.getTechList();
            NdefFormatable ndef = NdefFormatable.get(tag);
            String type = intent.getType();
            byte[] ID_tag = tag.getId();
            String searchedTech = Ndef.class.getName();

            Toast.makeText(this, "id: " + bytesToHex(ID_tag), Toast.LENGTH_SHORT).show();

            /* TODO: Broadcast id in app */
            Intent broadcast_id = new Intent();
            broadcast_id.setAction("ballot.corentin.emargementnfc.NFC_TAG_ID");
            broadcast_id.putExtra("id", bytesToHex(ID_tag));
            sendBroadcast(broadcast_id);

            this.finish();
        }
    }

    public void onNewTag(Tag tag) {
        handleIntent(getIntent());
    }


    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[((bytes.length) * 3) - 1];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 3] = hexArray[v >>> 4];
            hexChars[j * 3 + 1] = hexArray[v & 0x0F];
            if (j != bytes.length - 1)
                hexChars[j * 3 + 2] = ':';
        }
        return new String(hexChars);
    }
}
