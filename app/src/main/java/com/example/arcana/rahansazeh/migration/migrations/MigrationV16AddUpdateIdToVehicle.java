package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.VehicleDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/24/17.
 */

public class MigrationV16AddUpdateIdToVehicle extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 16;
    }

    @Override
    public void runMigration(Database db) {
        addColumn(db,
                VehicleDao.TABLENAME,
                "UPDATE_ID",
                "TEXT");
    }
}
