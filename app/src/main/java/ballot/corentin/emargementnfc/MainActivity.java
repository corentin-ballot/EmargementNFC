package ballot.corentin.emargementnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enregistrement(View view) {
        Intent monIntent = new Intent(this, RegisterActivity.class);
        startActivity(monIntent);
    }

    public void emargement(View view) {
        Intent monIntent = new Intent(this, EmargementsActivity.class);
        startActivity(monIntent);
    }
}
