package cr.ac.ucr.paraiso.tydilabs.tools;

import android.support.annotation.NonNull;

import java.util.regex.Pattern;

import cr.ac.ucr.paraiso.tydilabs.exceptions.InvalidQRCodeException;

/**
 * Project: Tydilabs
 * Date: 5/27/17
 *
 * @author ricardo
 */

public class AssetTools {

    public static final String ASSET_ID_NAME = "asset_id";

    /**
     * Parses the string encoded in a QR code to extract the meaningful data.
     *
     * @param qrCode string encoded in the scanned QR code
     * @return The numeric ID of the asset's qr code scanned.
     */
    public static int getCodeFromQR(@NonNull String qrCode) throws InvalidQRCodeException {
        String[] components = qrCode.split("_");

        if (components.length == 3) {
            boolean validQRCode;

            validQRCode = components[0].equals("ocp");
            validQRCode &= components[1].equals("activo");
            validQRCode &= Pattern.compile("\\d+").matcher(components[2]).matches();

            if (validQRCode) {
                return Integer.parseInt(components[2]);
            }
        }

        throw new InvalidQRCodeException();
    }

}
