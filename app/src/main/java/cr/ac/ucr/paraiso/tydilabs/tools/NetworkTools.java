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

import java.net.URL;
import java.net.URLConnection;

import cr.ac.ucr.paraiso.tydilabs.configuration.ConfigManager;

/**
 * Project: Tydilabs
 * Date: 5/27/17
 *
 * @author ricardo
 */

public class NetworkTools {

    private static final boolean BYPASS = true; // for development purposes

    public static boolean isOnValidNetwork(Context ctx) {
        ConnectivityManager connMgr = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        boolean onValidNetwork = false;
        if (activeInfo != null && activeInfo.isConnected()) {
            onValidNetwork = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            onValidNetwork &= activeInfo.getType() != ConnectivityManager.TYPE_MOBILE;
        }

        return onValidNetwork || NetworkTools.BYPASS;
    }

    public interface APIRequestCallback<T> {
        void onResponse(T response);

        void onFailure(Throwable t);
    }

    public static String toFullURL(String url) {
        if (!url.startsWith("http")) {
            url = String.format("http://%s", url);
        }

        return url;
    }

    public static void loadImage(String path, View view) {
        ConfigManager config = new ConfigManager(view.getContext());
        final ImageView imageView = (ImageView) view;
        final ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(view.getContext()));
        String url = config.getUrl() + path;
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
