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
        addColumn(db, OutgoingVehicleRecordDao.TABLENAME,
                OutgoingVehicleRecordDao.Properties.ClientId.columnName,
                "TEXT");

        addColumn(db, OutgoingPassengerRecordDao.TABLENAME,
                OutgoingPassengerRecordDao.Properties.ClientId.columnName,
                "TEXT");
    }
}
