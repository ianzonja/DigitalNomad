package entities;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Davor on 18.11.2017..
 */

@Database(name = MainDatabase.NAME, version = MainDatabase.VERSION)
public class MainDatabase {
    public static final String NAME = "User";
    public static final int VERSION = 1;
}
