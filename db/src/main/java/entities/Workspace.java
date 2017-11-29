package entities;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Davor on 21.11.2017..
 */

@Table(database = MainDatabase.class)
public class Workspace extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column int idworkspace;
    @Column String name;
    @Column String description;
    @Column String address;
    @Column String country;
    @Column String town;
    @Column double latitude;
    @Column double longitude;
   // @ForeignKey(tableClass = User.class)
    //User user;

    public Workspace() {

    }

    public Workspace(String name, String description, String address, String country, String town, double latitude, double longitude/*, User user*/) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.country = country;
        this.town = town;
        this.latitude = latitude;
        this.longitude = longitude;
      //  this.user = user;
    }

    public int getIdworkspace() {
        return idworkspace;
    }

    public void setIdworkspace(int idworkspace) {
        this.idworkspace = idworkspace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
