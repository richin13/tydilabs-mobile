package cr.ac.ucr.paraiso.tydilabs.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;

import cr.ac.ucr.paraiso.tydilabs.R;
import cr.ac.ucr.paraiso.tydilabs.exceptions.InvalidQRCodeException;
import cr.ac.ucr.paraiso.tydilabs.tools.AssetTools;
import cr.ac.ucr.paraiso.tydilabs.ui.fragments.ErrorDialogFragment;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class CodeReaderActivity extends FragmentActivity implements ZXingScannerView.ResultHandler {
    private static final int MY_PERMISSIONS_REQUEST_READ_CAMERA = 10;
    private ZXingScannerView mScannerView;

    private Runnable delayBetweenScans = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    // starts the scanning back up again
                    mScannerView.resumeCameraPreview(CodeReaderActivity.this);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
        mScannerView = new ZXingScannerView(this);
        mScannerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(mScannerView);
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_READ_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v("DEBUG", "Permissions granted");

                } else {
                    System.exit(0);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    protected void onStop() {
        super.onStop();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        mScannerView.setSystemUiVisibility(View.VISIBLE);
        CharSequence txt = result.getText();

        try {
            int code = AssetTools.getCodeFromQR(txt.toString());

            Intent intent = new Intent(CodeReaderActivity.this, AssetShowActivity.class);
            intent.putExtra(AssetTools.ASSET_ID_NAME, code);

            startActivity(intent);
        } catch (InvalidQRCodeException e) {
            e.printStackTrace();
            DialogFragment message = ErrorDialogFragment.newInstance(R.string.message_qr, R.string.message_qr_title);
            message.show(getSupportFragmentManager(), "error");
        }

        new Thread(delayBetweenScans).start();
    }
}
