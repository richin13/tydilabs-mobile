package cr.ac.ucr.paraiso.tydilabs.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Project: Tydilabs
 * Date: 5/27/17
 *
 * @author ricardo
 */

public class NetworkTools {

    private static final boolean BYPASS = true; // for development purposes

    public static final String URL_DEV = "http://192.168.43.149:3000";

    public static final String URL_PROD = "http://163.X.X.X";

    public static boolean isOnValidNetwork(Context ctx) {
        ConnectivityManager connMgr = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        boolean onValidNetwork = false;
        if (activeInfo != null && activeInfo.isConnected()) {
            onValidNetwork |= activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            onValidNetwork &= activeInfo.getType() != ConnectivityManager.TYPE_MOBILE;
        }

        return onValidNetwork || NetworkTools.BYPASS;
    }

    public interface APIRequestCallback<T> {
        void onResponse(T response);

        void onFailure(Throwable t);

    }

    private static String photoUrl(String path) {
        return URL_DEV + path;
    }

    public static void loadImage(String path, View view) {
        final ImageView imageView = (ImageView) view;
        final ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(view.getContext()));
        String url = NetworkTools.photoUrl(path);
        try {
            imageLoader.loadImage(url, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    imageView.setImageBitmap(loadedImage);
                    imageLoader.destroy();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
