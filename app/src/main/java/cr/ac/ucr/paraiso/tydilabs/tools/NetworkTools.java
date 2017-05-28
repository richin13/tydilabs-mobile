package cr.ac.ucr.paraiso.tydilabs.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

    public static String photoUrl(String path) {
        return URL_DEV + "/assetz/" + path;
    }

}
