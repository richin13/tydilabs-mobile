package cr.ac.ucr.paraiso.tydilabs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by daniel on 6/11/17.
 */

public class AssetRevision {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("revision")
    @Expose
    private int idRevision;

    @SerializedName("asset")
    @Expose
    private int idAsset;

    public AssetRevision() {
    }

    public AssetRevision(Integer id, int idRevision, int idAsset) {
        this.id = id;
        this.idRevision = idRevision;
        this.idAsset = idAsset;
    }

    public AssetRevision(int idRevision, int idAsset) {
        this.idRevision = idRevision;
        this.idAsset = idAsset;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdRevision() {
        return idRevision;
    }

    public void setIdRevision(int idRevision) {
        this.idRevision = idRevision;
    }

    public int getIdAsset() {
        return idAsset;
    }

    public void setIdAsset(int idAsset) {
        this.idAsset = idAsset;
    }
}
