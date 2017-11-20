package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.OutgoingVehicleRecordDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/20/17.
 */

public class MigrationV10AddTerminalToOutgoingVehicle extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 10;
    }

    @Override
    public void runMigration(Database db) {
        addColumnNotNull(db, OutgoingVehicleRecordDao.TABLENAME,
                OutgoingVehicleRecordDao.Properties.HasSelectedHeadTerminal.columnName,
                "INTEGER", "0");
    }
}
