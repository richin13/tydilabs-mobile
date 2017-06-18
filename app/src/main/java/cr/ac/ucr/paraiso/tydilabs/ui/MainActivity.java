package cr.ac.ucr.paraiso.tydilabs.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cr.ac.ucr.paraiso.tydilabs.R;
import cr.ac.ucr.paraiso.tydilabs.configuration.ConfigManager;

public class MainActivity extends AppCompatActivity {

    private View.OnClickListener setupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener scanCodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, CodeReaderActivity.class);
            startActivity(intent);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button setupBtn = (Button) findViewById(R.id.setupBtn);
        assert setupBtn != null;
        setupBtn.setOnClickListener(setupListener);

        Button scanBtn = (Button) findViewById(R.id.scanCodeBtn);
        assert scanBtn != null;
        scanBtn.setOnClickListener(scanCodeListener);

        ConfigManager mgr = new ConfigManager(getApplicationContext());

        TextView welcomeMessage = (TextView) findViewById(R.id.welcomeTV);
        welcomeMessage.setText(String.format(getString(R.string.welcome_user), mgr.getUser().getFullName()));
    }
}
