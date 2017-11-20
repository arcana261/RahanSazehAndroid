package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.OutgoingVehicleRecord;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecordDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/19/17.
 */

public class MigrationV8AddLoadAndUnloadToOutgoingVehicle extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 8;
    }

    @Override
    public void runMigration(Database db) {
        addColumn(db,
                OutgoingVehicleRecordDao.TABLENAME,
                OutgoingVehicleRecordDao.Properties.LoadPassengerCount.columnName,
                "INTEGER");

        addColumn(db,
                OutgoingVehicleRecordDao.TABLENAME,
                OutgoingVehicleRecordDao.Properties.UnloadPassengerCount.columnName,
                "INTEGER");
    }
}
