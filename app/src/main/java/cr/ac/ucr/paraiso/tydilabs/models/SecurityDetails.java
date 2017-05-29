package cr.ac.ucr.paraiso.tydilabs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

/**
 * Project: Tydilabs
 * Date: 5/28/17
 *
 * @author ricardo
 */

public class SecurityDetails {

    /*
    * This is development only
    * I swear I will change this once this app is in production. ;)
    * */
    private final String secretKey = "43=vDrDOw=r";

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClearTextPassword() {
        try {
            return AESCrypt.decrypt(secretKey, password.trim());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return password;
        }
    }
}
