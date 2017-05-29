package cr.ac.ucr.paraiso.tydilabs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Project: Tydilabs
 * Date: 5/26/17
 *
 * @author ricardo
 */

public class Asset {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("serial_number")
    @Expose
    private String serialNumber;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("plate_number")
    @Expose
    private int plateNumber;

    @SerializedName("quantity")
    @Expose
    private Object quantity;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("type_readable")
    @Expose
    private String typeNice;

    @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("photo_url")
    @Expose
    private String photoUrl;

    @SerializedName("area")
    @Expose
    private Area area;

    @SerializedName("category")
    @Expose
    private Category category;

    @SerializedName("warranty_details")
    @Expose
    private WarrantyDetails warrantyDetails;

    @SerializedName("technical_details")
    @Expose
    private TechnicalDetails technicalDetails;

    @SerializedName("security_details")
    @Expose
    private SecurityDetails securityDetails;

    @SerializedName("network_details")
    @Expose
    private NetworkDetails networkDetails;

    public Asset() {
    }

    public Asset(int id, String photo) {
        this.id = id;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(int plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Object getQuantity() {
        return quantity;
    }

    public void setQuantity(Object quantity) {
        this.quantity = quantity;
    }

    public String getStatusNice() {
        String niceStatus;

        switch (status) {
            case "service":
                niceStatus = "En servicio";
                break;
            case "loan":
                niceStatus = "En pŕestamo";
                break;
            case "maintenance":
                niceStatus = "En mantenimiento";
                break;
            case "retired":
                niceStatus = "Retirado";
                break;
            case "unassigned":
                niceStatus = "Sin asignar";
                break;
            default:
                niceStatus = status;
                break;
        }

        return niceStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeNice() {
        return typeNice;
    }

    public void setTypeNice(String typeNice) {
        this.typeNice = typeNice;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public WarrantyDetails getWarrantyDetails() {
        return warrantyDetails;
    }

    public void setWarrantyDetails(WarrantyDetails warrantyDetails) {
        this.warrantyDetails = warrantyDetails;
    }

    public TechnicalDetails getTechnicalDetails() {
        return technicalDetails;
    }

    public void setTechnicalDetails(TechnicalDetails technicalDetails) {
        this.technicalDetails = technicalDetails;
    }

    public SecurityDetails getSecurityDetails() {
        return securityDetails;
    }

    public void setSecurityDetails(SecurityDetails securityDetails) {
        this.securityDetails = securityDetails;
    }

    public NetworkDetails getNetworkDetails() {
        return networkDetails;
    }

    public void setNetworkDetails(NetworkDetails networkDetails) {
        this.networkDetails = networkDetails;
    }

}
