package cr.ac.ucr.paraiso.tydilabs.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cr.ac.ucr.paraiso.tydilabs.R;
import cr.ac.ucr.paraiso.tydilabs.tools.NetworkTools;

/**
 * Project: Tydilabs
 * Date: 5/1/17
 *
 * @author ricardo
 */

public class InvalidNetworkActivity extends AppCompatActivity {

    private View.OnClickListener btnRetryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            if(NetworkTools.isOnValidNetwork(getApplicationContext())) {
                intent = new Intent(InvalidNetworkActivity.this, LauncherActivity.class);
                startActivity(intent);
                finish();
            } else {
                Context ctx = getApplicationContext();
                Toast toast = Toast.makeText(ctx, R.string.invalid_network_solution, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_invalid_network);

        Button btnRetry = (Button) findViewById(R.id.btnRetry);
        assert btnRetry != null;
        btnRetry.setOnClickListener(btnRetryListener);
    }

}
