package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.OutgoingPassengerRecordDao;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecordDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/24/17.
 */

public class MigrationV13AddClientIdField extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 13;
    }

    @Override
    public void runMigration(Database db) {
        OutgoingVehicleRecordDao.dropTable(db, true);
        OutgoingVehicleRecordDao.createTable(db, false);
    }
}
