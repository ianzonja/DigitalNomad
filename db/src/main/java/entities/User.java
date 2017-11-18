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

    public void StoreData(String name, String last_name, String email, String password){
        User user = new User();
        user.name = name;
        user.last_name = last_name;
        user.email = email;
        user.password = password;
        user.save();
    }
}
