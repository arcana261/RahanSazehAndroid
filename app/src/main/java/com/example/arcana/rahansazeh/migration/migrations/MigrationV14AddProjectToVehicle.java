package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.VehicleDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/24/17.
 */

public class MigrationV14AddProjectToVehicle extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 14;
    }

    @Override
    public void runMigration(Database db) {
        addColumn(db, VehicleDao.TABLENAME,
                VehicleDao.Properties.ProjectId.columnName,
                "INTEGER");
    }
}
