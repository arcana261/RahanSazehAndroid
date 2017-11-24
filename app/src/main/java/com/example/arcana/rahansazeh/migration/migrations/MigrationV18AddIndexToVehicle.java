package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.VehicleDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/24/17.
 */

public class MigrationV18AddIndexToVehicle extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 18;
    }

    @Override
    public void runMigration(Database db) {
        addIndex(db, VehicleDao.TABLENAME, VehicleDao.Properties.License.columnName);
    }
}
