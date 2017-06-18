package cr.ac.ucr.paraiso.tydilabs.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import cr.ac.ucr.paraiso.tydilabs.R;
import cr.ac.ucr.paraiso.tydilabs.configuration.ConfigManager;
import cr.ac.ucr.paraiso.tydilabs.rest.impl.TydilabsAPI;
import cr.ac.ucr.paraiso.tydilabs.tools.NetworkTools;

/**
 * Project: Tydilabs
 * Date: 5/1/17
 *
 * @author ricardo
 */

public class LauncherActivity extends Activity {

    TydilabsAPI api;

    private NetworkTools.APIRequestCallback<Void> apiRequestCallback = new NetworkTools.APIRequestCallback<Void>() {
        @Override
        public void onResponse(Void response) {
            // Server is reachable
            Intent intent;
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("tydilabs", MODE_PRIVATE);
            boolean loggedIn = preferences.getBoolean("loggedIn", false);
            Log.v("INFO", Boolean.toString(loggedIn));

            if (!loggedIn) {
                intent = new Intent(LauncherActivity.this, LoginActivity.class);
            } else {
                intent = new Intent(LauncherActivity.this, MainActivity.class);
            }

            startActivity(intent);
            finish();
        }

        @Override
        public void onFailure(Throwable t) {
            Intent intent;
            intent = new Intent(LauncherActivity.this, SettingsActivity.class);
            intent.putExtra("notice", getString(R.string.invalid_url));
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Show the launcher for no less than two seconds.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ConfigManager config = new ConfigManager(getApplicationContext());

                if (NetworkTools.isOnValidNetwork(getApplicationContext())) {
                    String url = config.getUrl();
                    try {
                        api = TydilabsAPI.getTempInstance(null, url);
                        api.checkStatus(apiRequestCallback);
                    }catch (Exception e) {
                        apiRequestCallback.onFailure(e);
                    }
                } else {
                    Intent intent = new Intent(LauncherActivity.this, InvalidNetworkActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }).start();
    }


}
