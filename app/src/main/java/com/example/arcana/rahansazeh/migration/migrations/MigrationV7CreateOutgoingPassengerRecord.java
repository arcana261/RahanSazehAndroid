package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.OutgoingPassengerRecordDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/18/17.
 */

public class MigrationV7CreateOutgoingPassengerRecord extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 7;
    }

    @Override
    public void runMigration(Database db) {
        OutgoingPassengerRecordDao.createTable(db, false);
    }
}
