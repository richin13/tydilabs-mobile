package cr.ac.ucr.paraiso.tydilabs.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;

import cr.ac.ucr.paraiso.tydilabs.R;
import cr.ac.ucr.paraiso.tydilabs.configuration.ConfigManager;
import cr.ac.ucr.paraiso.tydilabs.exceptions.NotFoundException;
import cr.ac.ucr.paraiso.tydilabs.models.Asset;
import cr.ac.ucr.paraiso.tydilabs.models.AssetRevision;
import cr.ac.ucr.paraiso.tydilabs.models.NetworkDetails;
import cr.ac.ucr.paraiso.tydilabs.models.Revision;
import cr.ac.ucr.paraiso.tydilabs.models.SecurityDetails;
import cr.ac.ucr.paraiso.tydilabs.models.TechnicalDetails;
import cr.ac.ucr.paraiso.tydilabs.models.WarrantyDetails;
import cr.ac.ucr.paraiso.tydilabs.rest.impl.TydilabsAPI;
import cr.ac.ucr.paraiso.tydilabs.tools.APITools;
import cr.ac.ucr.paraiso.tydilabs.tools.AssetTools;
import cr.ac.ucr.paraiso.tydilabs.tools.NetworkTools;
import cr.ac.ucr.paraiso.tydilabs.ui.fragments.ErrorDialogFragment;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class AssetShowActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_ASSET_TO_REVISION = 2;

    private Asset asset;

    private TydilabsAPI api;

    private ProgressBar bar;

    private FloatingActionMenu fam;

    private NetworkTools.APIRequestCallback<Asset> apiRequestCallback = new NetworkTools.APIRequestCallback<Asset>() {
        @Override
        public void onResponse(Asset response) {
            asset = response;

            updateUi();

            /* Show the container*/
            LinearLayout layout = (LinearLayout) findViewById(R.id.assetDetailsContainer);
            assert layout != null;
            layout.setVisibility(VISIBLE);
            fam.setVisibility(VISIBLE);
            bar.setVisibility(INVISIBLE);
        }

        @Override
        public void onFailure(Throwable t) {
            bar.setVisibility(INVISIBLE);
            t.printStackTrace();

            if (t instanceof NotFoundException) {
                DialogFragment message = ErrorDialogFragment.newInstance(R.string.message_qr,
                        R.string.message_qr_title);
                message.show(getSupportFragmentManager(), "error");
            } else {
                DialogFragment message = ErrorDialogFragment.newInstance(R.string.generic_error,
                        R.string.generic_error_title);
                message.show(getSupportFragmentManager(), "error");
            }

            if (asset == null) {
                finish();
            }
        }
    };

    private NetworkTools.APIRequestCallback<AssetRevision> assetRevApiRequestCallback = new NetworkTools.APIRequestCallback<AssetRevision>() {
        @Override
        public void onResponse(AssetRevision response) {
            Toast.makeText(AssetShowActivity.this, R.string.revision_update_success, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Throwable t) {
            Toast.makeText(AssetShowActivity.this, R.string.revision_update_error, Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener addPictureListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fam.close(true);
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    };

    private View.OnClickListener addToRevisionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fam.close(true);

            Intent revisionSelect = new Intent(AssetShowActivity.this, RevisionsActivity.class);
            revisionSelect.setAction(REQUEST_ASSET_TO_REVISION + "");
            startActivityForResult(revisionSelect, REQUEST_ASSET_TO_REVISION);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_show);

        /* The progress bar to show the user we're performing some async task */
        bar = (ProgressBar) findViewById(R.id.progressBar);

        /* The API object to make requests to Tydilabs' Web Service */
        ConfigManager config = new ConfigManager(getApplicationContext());
        api = TydilabsAPI.getInstance(config.getUser(), config.getUrl());
        startApiRequest();


        fam = (FloatingActionMenu) findViewById(R.id.fam);
        assert fam != null;
        fam.setVisibility(INVISIBLE);

        FloatingActionButton fabAddPicture = (FloatingActionButton) findViewById(R.id.add_picture);
        assert fabAddPicture != null;
        fabAddPicture.setOnClickListener(addPictureListener);

        FloatingActionButton fabAddToRevision = (FloatingActionButton) findViewById(R.id.add_revision);
        assert fabAddToRevision != null;
        fabAddToRevision.setOnClickListener(addToRevisionListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            asset.setPhoto(APITools.getStringFromBitmap(imageBitmap));
            bar.setVisibility(VISIBLE);
            fam.close(true);
            api.assetUpdate(asset, apiRequestCallback);
        } else if (requestCode == REQUEST_ASSET_TO_REVISION && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Revision revision = new Gson().fromJson(extras.getString("revision"), Revision.class);
            AssetRevision assetRevision = new AssetRevision(revision.getId(), asset.getId());

            api.assetRevisionCreate(assetRevision, assetRevApiRequestCallback);
        }
    }

    private void startApiRequest() {
        Bundle args = getIntent().getExtras();
        int assetId = args.getInt(AssetTools.ASSET_ID_NAME);
        api.asset(assetId, apiRequestCallback);
    }

    private void updateUi() {
        /* This is *probably* the worst way to do this but hey!
         * this is my very first android app so I had to accept
         * that no further optimization can be done given my deadline.
         * I'm not always this bad! -- @richin13
          * */
        TextView editing;

        editing = textView(R.id.assetDescriptionTV);
        editing.setText(asset.getDescription());

        final ImageView imageView = (ImageView) findViewById(R.id.assetPhotoIV);
        assert imageView != null;

        NetworkTools.loadImage(asset.getPhotoUrl(), imageView);

        editing = textView(R.id.assetTypeTV);
        editing.setText(asset.getType());

        editing = textView(R.id.assetCategoryTV);
        editing.setText(asset.getCategory().getName());

        editing = textView(R.id.assetLocationTV);
        editing.setText(asset.getArea().getName());

        editing = textView(R.id.assetStatusTV);
        editing.setText(asset.getStatusNice());

        updateUiWithWarranty(asset.getWarrantyDetails());
        updateUiWithTechDetails(asset.getTechnicalDetails());
        updateUiWithNetworkDetails(asset.getNetworkDetails());
        updateUiWithSecurityDetails(asset.getSecurityDetails());
    }

    private void updateUiWithWarranty(WarrantyDetails wd) {
        if (wd != null) {
            TextView editing;

            editing = textView(R.id.warrantyDateTV);
            editing.setText(wd.getPurchaseDate());

            editing = textView(R.id.warrantyValid);
            editing.setText(wd.getDuration());

            editing = textView(R.id.warrantyAgent);
            editing.setText(wd.getAgentName());

            editing = textView(R.id.warrantyPhone);
            editing.setText(wd.getAgentPhone());
        } else {
            // find the layout that contains the warranty details
            // set its visibility to GONE
            LinearLayout layout = (LinearLayout) findViewById(R.id.warrantyContainer);
            assert layout != null;
            layout.setVisibility(GONE);
        }
    }

    private void updateUiWithTechDetails(TechnicalDetails td) {
        if (td != null) {
            TextView editing;

            editing = textView(R.id.tdCpuTV);
            editing.setText(td.getCpu());

            editing = textView(R.id.tdRamTV);
            editing.setText(String.format(getString(R.string.ad_td_memory_format), td.getRam()));

            editing = textView(R.id.tdHddTV);
            editing.setText(String.format(getString(R.string.ad_td_memory_format), td.getHdd()));

            editing = textView(R.id.tdOsTV);
            editing.setText(td.getOs());
        } else {
            LinearLayout layout = (LinearLayout) findViewById(R.id.technicalDetailsContainer);
            assert layout != null;
            layout.setVisibility(GONE);
        }
    }

    private void updateUiWithNetworkDetails(NetworkDetails nd) {
        if (nd != null) {
            TextView editing;

            editing = textView(R.id.ndIpTV);
            editing.setText(nd.getIp());

            editing = textView(R.id.ndMaskTV);
            editing.setText(nd.getMask());

            editing = textView(R.id.ndGatewayTV);
            editing.setText(nd.getGateway());

            editing = textView(R.id.ndDnsTV);
            editing.setText(nd.getDns());
        } else {
            LinearLayout layout = (LinearLayout) findViewById(R.id.networkDetailsContainer);
            assert layout != null;
            layout.setVisibility(GONE);
        }
    }

    private void updateUiWithSecurityDetails(SecurityDetails sd) {
        if (sd != null) {
            TextView editing;

            editing = textView(R.id.sdUsernameTV);
            editing.setText(sd.getUsername());

            editing = textView(R.id.sdPasswordTV);
            editing.setText(sd.getClearTextPassword());
        } else {
            LinearLayout layout = (LinearLayout) findViewById(R.id.securityDetailsContainer);
            assert layout != null;
            layout.setVisibility(GONE);
        }
    }

    private TextView textView(int id) {
        return (TextView) findViewById(id);
    }

}
