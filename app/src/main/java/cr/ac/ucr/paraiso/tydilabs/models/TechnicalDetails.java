package cr.ac.ucr.paraiso.tydilabs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Project: Tydilabs
 * Date: 5/28/17
 *
 * @author ricardo
 */

public class TechnicalDetails {
    @SerializedName("cpu")
    @Expose
    private String cpu;
    @SerializedName("ram")
    @Expose
    private int ram;
    @SerializedName("hdd")
    @Expose
    private int hdd;
    @SerializedName("os")
    @Expose
    private String os;
    @SerializedName("other")
    @Expose
    private String other;

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getHdd() {
        return hdd;
    }

    public void setHdd(int hdd) {
        this.hdd = hdd;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
