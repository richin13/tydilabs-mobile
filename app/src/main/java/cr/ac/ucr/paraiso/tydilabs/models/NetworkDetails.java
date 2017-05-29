package cr.ac.ucr.paraiso.tydilabs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Project: Tydilabs
 * Date: 5/28/17
 *
 * @author ricardo
 */

public class NetworkDetails {

    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("mask")
    @Expose
    private String mask;
    @SerializedName("gateway")
    @Expose
    private String gateway;
    @SerializedName("dns")
    @Expose
    private String dns;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }
}
