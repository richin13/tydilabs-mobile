package cr.ac.ucr.paraiso.tydilabs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import cr.ac.ucr.paraiso.tydilabs.R;
import cr.ac.ucr.paraiso.tydilabs.configuration.ConfigManager;
import cr.ac.ucr.paraiso.tydilabs.models.Asset;
import cr.ac.ucr.paraiso.tydilabs.models.Revision;
import cr.ac.ucr.paraiso.tydilabs.rest.impl.TydilabsAPI;
import cr.ac.ucr.paraiso.tydilabs.tools.AssetTools;
import cr.ac.ucr.paraiso.tydilabs.tools.NetworkTools;
import cr.ac.ucr.paraiso.tydilabs.ui.adapters.AssetAdapter;

public class RevisionShowActivity extends AppCompatActivity {

    private NetworkTools.APIRequestCallback<Revision> apiRequestCallback = new NetworkTools.APIRequestCallback<Revision>() {
        @Override
        public void onResponse(final Revision revision) {
            AssetAdapter adapter = new AssetAdapter(getApplicationContext(), revision.getAssets());

            ListView listView = (ListView) findViewById(R.id.assetList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Asset selectedAsset = revision.getAssets().get(position);

                    Intent intent = new Intent(RevisionShowActivity.this, AssetShowActivity.class);
                    intent.putExtra(AssetTools.ASSET_ID_NAME, selectedAsset.getId());

                    startActivity(intent);
                }
            });

            updateUi(revision);
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
        setContentView(R.layout.activity_revision_show2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Tabs setup
        TabHost tabs = (TabHost)findViewById(R.id.revisionTabHost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("revisionTab");
        spec.setContent(R.id.revisionTab);
        spec.setIndicator("Revision");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("assetsTab");
        spec.setContent(R.id.assetsTab);
        spec.setIndicator("Activos");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        // API calls
        ConfigManager config = new ConfigManager(getApplicationContext());
        TydilabsAPI api = TydilabsAPI.getInstance(config.getUser());
        startApiRequest(api);
    }

    private void startApiRequest(TydilabsAPI api) {
        Bundle args = getIntent().getExtras();
        int assetId = args.getInt("revision_id");
        api.revision(assetId, apiRequestCallback);
    }

    private void updateUi(Revision revision) {
        setTitle(revision.getName());
        TextView revisionDescTextView = (TextView) findViewById(R.id.revisionDescription);
        revisionDescTextView.setText(revision.getDescription());

        TextView assetCount = (TextView) findViewById(R.id.assetCount);
        assetCount.append(" " + String.valueOf(revision.getAssets().size()));

        TextView revStatus = (TextView) findViewById(R.id.revisionStatus);
        revStatus.append(" " + (revision.isOpen() ? "Abierta" : "Cerrada"));
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
}
