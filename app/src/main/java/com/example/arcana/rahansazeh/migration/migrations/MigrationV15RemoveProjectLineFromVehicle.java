package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.OutgoingVehicleRecord;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecordDao;
import com.example.arcana.rahansazeh.model.VehicleDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/24/17.
 */

public class MigrationV15RemoveProjectLineFromVehicle extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 15;
    }

    @Override
    public void runMigration(Database db) {
        VehicleDao.dropTable(db, true);
        OutgoingVehicleRecordDao.dropTable(db, true);

        VehicleDao.createTable(db, false);
        OutgoingVehicleRecordDao.createTable(db, false);
    }
}
