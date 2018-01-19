package entities;

import com.raizlabs.android.dbflow.annotation.Column;
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
    @Column String country;
    @Column String city;
    @Column String address;
   // @ForeignKey(tableClass = User.class)
    //User user;

    public Workspace() {

    }

    public Workspace(String name, String country, String city, String address) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public int getIdworkspace() {
        return idworkspace;
    }

    public void setIdworkspace(int idworkspace) {
        this.idworkspace = idworkspace;
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

    public String getCity() {
        return city;
    }

    public void setTown(String city) { this.city = city; }
}
