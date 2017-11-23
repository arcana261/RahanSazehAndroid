package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.OutgoingPassengerRecordDao;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecordDao;
import com.example.arcana.rahansazeh.model.ProjectDao;
import com.example.arcana.rahansazeh.model.ProjectLineDao;
import com.example.arcana.rahansazeh.model.UserDao;
import com.example.arcana.rahansazeh.model.VehicleDao;
import com.example.arcana.rahansazeh.model.VehicleTypeDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/23/17.
 */

public class MigrationV12RecreateAllTables extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 12;
    }

    @Override
    public void runMigration(Database db) {
        OutgoingPassengerRecordDao.dropTable(db, true);
        OutgoingVehicleRecordDao.dropTable(db, true);
        VehicleDao.dropTable(db, true);
        VehicleTypeDao.dropTable(db, true);
        ProjectLineDao.dropTable(db, true);
        ProjectDao.dropTable(db, true);
        UserDao.dropTable(db, true);

        UserDao.createTable(db, false);
        ProjectDao.createTable(db, true);
        ProjectLineDao.createTable(db, true);
        VehicleTypeDao.createTable(db, true);
        VehicleDao.createTable(db, true);
        OutgoingVehicleRecordDao.createTable(db, true);
        OutgoingPassengerRecordDao.createTable(db, true);
    }
}
