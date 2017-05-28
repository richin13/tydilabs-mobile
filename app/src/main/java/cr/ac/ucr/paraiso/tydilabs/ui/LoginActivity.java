package cr.ac.ucr.paraiso.tydilabs.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cr.ac.ucr.paraiso.tydilabs.R;
import cr.ac.ucr.paraiso.tydilabs.configuration.ConfigManager;
import cr.ac.ucr.paraiso.tydilabs.exceptions.BadRequestException;
import cr.ac.ucr.paraiso.tydilabs.models.User;
import cr.ac.ucr.paraiso.tydilabs.rest.impl.TydilabsAPI;
import cr.ac.ucr.paraiso.tydilabs.tools.NetworkTools;
import cr.ac.ucr.paraiso.tydilabs.ui.fragments.ErrorDialogFragment;

/**
 * Project: Tydilabs
 * Date: 5/1/17
 *
 * @author ricardo
 */

public class LoginActivity extends FragmentActivity {

    // Uses Configuration manager (wrapper for SharedPreferences)
    ConfigManager config;

    // Uses TydilabsAPI
    TydilabsAPI api;

    /**
     * The login api request callback handler (fancy, huh?)
     */
    private NetworkTools.APIRequestCallback<User> apiRequestCallback = new NetworkTools.APIRequestCallback<User>() {
        @Override
        public void onResponse(User user) {
            config.putObject("loggedInUser", user);
            config.putBoolean("loggedIn", true);
            config.apply();

            // Now open the next activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onFailure(Throwable t) {
            t.printStackTrace();

            if (t instanceof BadRequestException) {
                // Invalid credentials
                Context ctx = getApplicationContext();
                Toast toast = Toast.makeText(ctx, R.string.invalid_pin, Toast.LENGTH_LONG);
                toast.show();
            } else {
                DialogFragment errorDialogFragment = new ErrorDialogFragment();
                errorDialogFragment.show(getSupportFragmentManager(), "error");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        config = new ConfigManager(getApplicationContext());
        /*
        * There's no point on using config.getUser here. Let's use
        * a empty user instance instead.
        * */
        api = TydilabsAPI.getInstance(new User());

        Button loginBtn = (Button) findViewById(R.id.login_button);
        assert loginBtn != null;
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = getPinTexts();
                Log.d("DEBUG", "Using pin --> " + pin);
                try {
                    api.login(new User(pin), apiRequestCallback);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getPinTexts() {
        EditText pinET = (EditText) findViewById(R.id.pin_text);
        assert pinET != null;
        return pinET.getText().toString();
    }
}
