package ballot.corentin.emargementnfc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EtudiantActivity extends AppCompatActivity {

    EditText et_id;
    EditText et_nom;
    EditText et_prenom;
    EditText et_carte_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant);

        et_id = findViewById(R.id.etudiant_id);
        et_nom = findViewById(R.id.etudiant_nom);
        et_prenom = findViewById(R.id.etudiant_prenom);
        et_carte_id = findViewById(R.id.etudiant_carte_id);
    }
}
