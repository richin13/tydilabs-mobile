package cr.ac.ucr.paraiso.tydilabs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import cr.ac.ucr.paraiso.tydilabs.R;
import cr.ac.ucr.paraiso.tydilabs.configuration.ConfigManager;
import cr.ac.ucr.paraiso.tydilabs.models.Revision;
import cr.ac.ucr.paraiso.tydilabs.rest.impl.TydilabsAPI;
import cr.ac.ucr.paraiso.tydilabs.tools.NetworkTools;
import cr.ac.ucr.paraiso.tydilabs.ui.adapters.RevisionAdapter;

public class RevisionsActivity extends AppCompatActivity {

    ConfigManager config;
    TydilabsAPI api;

    private NetworkTools.APIRequestCallback<List<Revision>> apiRequestCallback = new NetworkTools.APIRequestCallback<List<Revision>>() {
        @Override
        public void onResponse(List<Revision> revisions) {

            RevisionAdapter adapter = new RevisionAdapter(getApplicationContext(), revisions);

            ListView listView = (ListView) findViewById(R.id.revision_list);
            listView.setAdapter(adapter);
        }

        @Override
        public void onFailure(Throwable t) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    R.string.revisions_fetch_error, Toast.LENGTH_LONG);
            toast.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisions);
        setTitle("Revisiones");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fetchRevisions();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }

    private void fetchRevisions() {
        config = new ConfigManager(getApplicationContext());
        api = TydilabsAPI.getInstance(config.getUser());
        api.revisions(apiRequestCallback);
    }
}
