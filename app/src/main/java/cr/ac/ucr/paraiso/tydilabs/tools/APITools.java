package cr.ac.ucr.paraiso.tydilabs.tools;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Project: Tydilabs
 * Date: 5/28/17
 *
 * @author ricardo
 */

public class APITools {


    public static String getStringFromBitmap(Bitmap bitmapPicture) {
        /* Blatantly copied from https://stackoverflow.com/a/30824334
        * Thank you much, Remy.
        * */
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return "data:image/png;base64," + encodedImage;
    }

}
