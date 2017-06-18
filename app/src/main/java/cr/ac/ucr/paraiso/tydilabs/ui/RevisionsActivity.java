package cr.ac.ucr.paraiso.tydilabs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

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
        public void onResponse(final List<Revision> revisions) {

            RevisionAdapter adapter = new RevisionAdapter(getApplicationContext(), revisions);

            ListView listView = (ListView) findViewById(R.id.revision_list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    if (getIntent().getAction() != null) { // Called for result
                        Intent i = getIntent();
                        i.putExtra("revision", new Gson().toJson(revisions.get(position)));
                        setResult(RESULT_OK, i);
                        finish();
                    }

                    else {
                        Intent i = new Intent(RevisionsActivity.this, RevisionShowActivity.class);
                        i.putExtra("revision_id", revisions.get(position).getId());
                        startActivity(i);
                    }
                }
            });
        }

        @Override
        public void onFailure(Throwable t) {
            Toast.makeText(getApplicationContext(), R.string.revisions_fetch_error, Toast.LENGTH_LONG).show();
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
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchRevisions() {
        config = new ConfigManager(getApplicationContext());
        api = TydilabsAPI.getInstance(config.getUser(), config.getUrl());
        api.revisions(apiRequestCallback);
    }
}
