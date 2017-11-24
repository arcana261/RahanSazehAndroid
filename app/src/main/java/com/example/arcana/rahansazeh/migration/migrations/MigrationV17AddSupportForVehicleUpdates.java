package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.KeyValueDao;
import com.example.arcana.rahansazeh.model.VehicleDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/24/17.
 */

public class MigrationV17AddSupportForVehicleUpdates extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 17;
    }

    @Override
    public void runMigration(Database db) {
        VehicleDao.dropTable(db, true);

        VehicleDao.createTable(db, false);
        KeyValueDao.createTable(db, false);
    }
}
