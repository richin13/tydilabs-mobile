package cr.ac.ucr.paraiso.tydilabs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Project: Tydilabs
 * Date: 5/26/17
 *
 * @author ricardo
 */
public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    
    @SerializedName("email")
    @Expose
    private String email;
    
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    
    @SerializedName("pin")
    @Expose
    private Object pin;
    
    @SerializedName("name")
    @Expose
    private String name;
    
    @SerializedName("lastname")
    @Expose
    private String lastName;
    
    @SerializedName("can_login")
    @Expose
    private Boolean canLogin;

    public User() {
    }

    public User(Object pin) {
        this.pin = pin;
    }

    public User(Integer id, String email, String createdAt, String updatedAt, Object pin,
                String name, String lastName, Boolean canLogin) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.pin = pin;
        this.name = name;
        this.lastName = lastName;
        this.canLogin = canLogin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getPin() {
        return pin;
    }

    public void setPin(Object pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getCanLogin() {
        return canLogin;
    }

    public void setCanLogin(Boolean canLogin) {
        this.canLogin = canLogin;
    }

    public boolean isAdmin() {
        return this.canLogin;
    }

    public String getFullName() {
        return String.format("%s %s", this.getName(), this.getLastName());
    }

    @Override
    public String toString() {
        return this.getFullName();
    }

}
