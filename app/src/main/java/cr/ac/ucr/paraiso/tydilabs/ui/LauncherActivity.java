package cr.ac.ucr.paraiso.tydilabs.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * Project: Tydilabs
 * Date: 5/1/17
 *
 * @author ricardo
 */

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
