package entities;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Davor on 18.11.2017..
 */

@Table(database = MainDatabase.class)
public class User extends BaseModel {
    public User() {

    }

    public User(String name, String last_name, String email, String password) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.image_url = password;
    }

    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    @PrimaryKey (autoincrement = true)
    @Column int iduser;
    @Column String name;
    @Column String last_name;
    @Column String email;
    @Column String image_url;


    public void StoreData(String name, String last_name, String email, String image_url){
        User user = new User();
        user.name = name;
        user.last_name = last_name;
        user.email = email;
        user.image_url = image_url;
        user.save();
    }
}
