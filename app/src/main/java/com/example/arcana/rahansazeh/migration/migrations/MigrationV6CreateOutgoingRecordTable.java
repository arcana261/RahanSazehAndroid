package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.OutgoingVehicleRecordDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/16/17.
 */

public class MigrationV6CreateOutgoingRecordTable extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 6;
    }

    @Override
    public void runMigration(Database db) {
        OutgoingVehicleRecordDao.createTable(db, false);
    }
}
