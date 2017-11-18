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

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public User(int iduser) {

        this.iduser = iduser;
    }

    @PrimaryKey (autoincrement = true)
    @Column int iduser;
    @Column String name;
    @Column String last_name;
    @Column String email;
    @Column String password;
    @Column String image_url;
}
