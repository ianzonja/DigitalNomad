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
    @ForeignKey(tableClass = User.class)
    User user;
}
