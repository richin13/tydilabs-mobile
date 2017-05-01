package cr.ac.ucr.paraiso.tydilabs.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cr.ac.ucr.paraiso.tydilabs.R;

/**
 * Project: Tydilabs
 * Date: 5/1/17
 *
 * @author ricardo
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = (Button) findViewById(R.id.login_button);
        assert loginBtn != null;
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText pinET = (EditText) findViewById(R.id.pin_text);
                assert pinET != null;
                if (validPin(pinET.getText().toString())) {
                    // First we save the successful login in SharedPreferences
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("tydilabs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("loggedIn", true);
                    editor.apply();

                    // Now open the next activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Context ctx = getApplicationContext();
                    Toast toast = Toast.makeText(ctx, "PIN inválido", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    private boolean validPin(@NonNull String pinTxt) {
        // complex stuff where we consult the RESTful WS to check
        // whether the given PIN is valid or not.
        return pinTxt.length() == 6 && 123456 == Integer.parseInt(pinTxt);
    }
}
