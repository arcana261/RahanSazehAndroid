package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.VehicleDao;
import com.gurkashi.lava.lambdas.Predicate;
import com.gurkashi.lava.queries.stracture.Queriable;

import org.greenrobot.greendao.database.Database;

import java.util.List;

/**
 * Created by arcana on 11/24/17.
 */

public class MigrationV14AddProjectToVehicle extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 14;
    }

    @Override
    public void runMigration(Database db) {
        List<ColumnDescription> descriptions = describeTable(db, VehicleDao.TABLENAME);
        boolean hasColumn = Queriable.create(descriptions)
                .exists(new Predicate<ColumnDescription>() {
                    @Override
                    public boolean predict(ColumnDescription columnDescription) {
                        return VehicleDao.Properties.ProjectId.columnName.equals(columnDescription.columnName);
                    }
                }).execute();

        if (!hasColumn) {
            addColumn(db, VehicleDao.TABLENAME,
                    VehicleDao.Properties.ProjectId.columnName,
                    "INTEGER");
        }
    }
}
