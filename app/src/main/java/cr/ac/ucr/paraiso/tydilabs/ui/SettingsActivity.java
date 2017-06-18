package cr.ac.ucr.paraiso.tydilabs.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cr.ac.ucr.paraiso.tydilabs.R;
import cr.ac.ucr.paraiso.tydilabs.configuration.ConfigManager;
import cr.ac.ucr.paraiso.tydilabs.rest.impl.TydilabsAPI;
import cr.ac.ucr.paraiso.tydilabs.tools.NetworkTools;

public class SettingsActivity extends AppCompatActivity {

    private ConfigManager config;

    private TextView urlTV;

    private NetworkTools.APIRequestCallback<Void> apiRequestCallback = new NetworkTools.APIRequestCallback<Void>() {
        @Override
        public void onResponse(Void response) {
            runOnUiThread(new Runnable() {
                public void run() {
                    TextView verificationResultTV = (TextView) findViewById(R.id.verificationResultTV);
                    if (verificationResultTV != null) {
                        verificationResultTV.setText(getString(R.string.verify_result_ok));
                    }
                }
            });
        }

        @Override
        public void onFailure(Throwable t) {
            runOnUiThread(new Runnable() {
                public void run() {
                    TextView verificationResultTV = (TextView) findViewById(R.id.verificationResultTV);
                    if (verificationResultTV != null) {
                        verificationResultTV.setText(getString(R.string.verify_result_fail));
                    }
                }
            });
        }
    };

    private View.OnClickListener verifyUrlListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                String ip = urlTV.getText().toString();
                TydilabsAPI api = TydilabsAPI.getTempInstance(config.getUser(), ip);
                api.checkStatus(apiRequestCallback);
                TextView verificationResultTV = (TextView) findViewById(R.id.verificationResultTV);
                verificationResultTV.setText(String.format(getString(R.string.verifying), ip));
            } catch (Exception e) {
                apiRequestCallback.onFailure(e);
            }
        }
    };

    private View.OnClickListener saveUrlListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String ip = urlTV.getText().toString();

            config.putString("serverUrl", ip).commit();

            Intent launcher = new Intent(SettingsActivity.this, LauncherActivity.class);
            startActivity(launcher);
            finish();
        }
    };

    private View.OnClickListener clearPreferencesListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            config.clear().commit();

            Intent launcher = new Intent(SettingsActivity.this, LauncherActivity.class);
            startActivity(launcher);
            finish();

            Toast.makeText(getApplicationContext(), "Se han limpiado los datos", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener signOutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            config.remove("loggedIn").commit();

            Intent launcher = new Intent(SettingsActivity.this, LauncherActivity.class);
            startActivity(launcher);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        config = new ConfigManager(getApplicationContext());

        Button b = (Button) findViewById(R.id.verifyUrlBtn);
        assert b != null;
        b.setOnClickListener(verifyUrlListener);

        b = (Button) findViewById(R.id.saveUrlBtn);
        assert b != null;
        b.setOnClickListener(saveUrlListener);

        b = (Button) findViewById(R.id.clearDataBtn);
        assert b != null;
        b.setOnClickListener(clearPreferencesListener);

        b = (Button) findViewById(R.id.signOutBtn);
        assert b != null;
        b.setOnClickListener(signOutListener);
        b.setEnabled(config.getUser() != null);

        urlTV = (EditText) findViewById(R.id.serverUrlET);
        urlTV.setText(config.getUrl());

        Bundle args = getIntent().getExtras();
        if (args != null) {
            String message = args.getString("notice", null);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
