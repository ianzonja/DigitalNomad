package entities;

import android.os.AsyncTask;

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

    public User(int id,String name, String email, String url) {
        this.idUser = id;
        this.name = name;
        this.email = email;
        this.image_url = url;
    }

    public int getIduser() {
        return idUser;
    }

    public void setIduser(int iduser) {
        this.idUser = iduser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @PrimaryKey //(autoincrement = true)
    @Column int idUser;
    @Column String name;
    @Column String email;
    @Column String image_url;
}
