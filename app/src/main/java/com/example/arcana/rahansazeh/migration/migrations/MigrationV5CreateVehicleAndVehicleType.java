package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.VehicleDao;
import com.example.arcana.rahansazeh.model.VehicleTypeDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/10/17.
 */

public class MigrationV5CreateVehicleAndVehicleType extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 5;
    }

    @Override
    public void runMigration(Database db) {
        VehicleTypeDao.dropTable(db, true);
        VehicleDao.dropTable(db, true);
        VehicleTypeDao.createTable(db, false);
        VehicleDao.createTable(db, false);
    }
}
