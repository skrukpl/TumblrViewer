package com.tumblrviewer.app;

import com.prashantsolanki.secureprefmanager.SecurePrefManagerInit;
import com.tumblrviewer.BuildConfig;
import com.tumblrviewer.api.ApiManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by sebastian on 02.04.2017.
 */

public class Application extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        new SecurePrefManagerInit.Initializer(getApplicationContext())
                .useEncryption(true)
                .initialize();

        ApiManager.initApiManager();

        if (BuildConfig.REPORTLOGS)
            Timber.plant(new Timber.DebugTree());

    }
}
