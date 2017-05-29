package cr.ac.ucr.paraiso.tydilabs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Project: Tydilabs
 * Date: 5/28/17
 *
 * @author ricardo
 */

public class WarrantyDetails {

    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("months")
    @Expose
    private int months;
    @SerializedName("agent_name")
    @Expose
    private String agentName;
    @SerializedName("agent_phone")
    @Expose
    private String agentPhone;
    @SerializedName("duration")
    @Expose
    private String duration;

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentPhone() {
        return agentPhone;
    }

    public void setAgentPhone(String agentPhone) {
        this.agentPhone = agentPhone;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
