package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.OutgoingPassengerRecordDao;
import com.gurkashi.lava.lambdas.Selector;
import com.gurkashi.lava.queries.stracture.Queriable;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/19/17.
 */

public class MigrationV9AddPrimaryKeyToOutgoingPassenger extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 9;
    }

    @Override
    public void runMigration(Database db) {
        String tableName = OutgoingPassengerRecordDao.TABLENAME;
        String tempTable = tableName + "_temp";

        String[] columns = Queriable.create(new Property[] {
                OutgoingPassengerRecordDao.Properties.Day,
                OutgoingPassengerRecordDao.Properties.FinishHour,
                OutgoingPassengerRecordDao.Properties.FinishMinute,
                OutgoingPassengerRecordDao.Properties.Month,
                OutgoingPassengerRecordDao.Properties.PassengerCount,
                OutgoingPassengerRecordDao.Properties.ProjectId,
                OutgoingPassengerRecordDao.Properties.ProjectLineId,
                OutgoingPassengerRecordDao.Properties.StartHour,
                OutgoingPassengerRecordDao.Properties.StartMinute,
                OutgoingPassengerRecordDao.Properties.UserId,
                OutgoingPassengerRecordDao.Properties.Year
        }).map(new Selector<Property, String>() {
            @Override
            public String select(Property property) {
                return property.columnName;
            }
        }).execute().toArray(new String[0]);

        renameTable(db, tableName, tempTable);
        OutgoingPassengerRecordDao.createTable(db, false);
        copyTable(db, tempTable, tableName, columns, columns);
        dropTable(db, tempTable);
    }
}
