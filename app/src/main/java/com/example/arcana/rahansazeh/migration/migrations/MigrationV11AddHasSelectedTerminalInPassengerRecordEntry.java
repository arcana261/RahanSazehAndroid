package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.OutgoingPassengerRecordDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/22/17.
 */

public class MigrationV11AddHasSelectedTerminalInPassengerRecordEntry extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 11;
    }

    @Override
    public void runMigration(Database db) {
        addColumn(db, OutgoingPassengerRecordDao.TABLENAME,
                OutgoingPassengerRecordDao.Properties.HasSelectedHeadTerminal.columnName,
                "INTEGER");
    }
}
