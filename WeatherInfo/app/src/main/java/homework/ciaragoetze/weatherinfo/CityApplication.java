package homework.ciaragoetze.weatherinfo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ciaragoetze on 11/15/17.
 */

public class CityApplication extends Application {

    private Realm realmCity;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

    public void openRealm() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realmCity = Realm.getInstance(config);
    }

    public void closeRealm() {
        realmCity.close();
    }

    public Realm getRealmCity() {
        return realmCity;
    }
}
