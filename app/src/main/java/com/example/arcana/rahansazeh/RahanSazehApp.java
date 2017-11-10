package com.example.arcana.rahansazeh;

import android.app.Application;
import android.content.res.Configuration;

import com.example.arcana.rahansazeh.migration.DatabaseUpgradeHelper;
import com.example.arcana.rahansazeh.model.DaoMaster;
import com.example.arcana.rahansazeh.model.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/10/17.
 */

public class RahanSazehApp extends Application {
    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseUpgradeHelper helper = new DatabaseUpgradeHelper(this, ENCRYPTED ? "rahansazeh-db-encrypted" : "rahansazeh-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
