package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.migration.Migration;
import com.example.arcana.rahansazeh.model.Project;
import com.example.arcana.rahansazeh.model.ProjectDao;
import com.example.arcana.rahansazeh.model.ProjectLineDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/10/17.
 */

public class MigrationV3AddExternalIdToProjectAndProjectLine extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 3;
    }

    @Override
    public void runMigration(Database db) {
        addColumn(db, ProjectDao.TABLENAME, ProjectDao.Properties.ExternalId.columnName,
                "INTEGER");
        addUniqueIndex(db, ProjectDao.TABLENAME, ProjectDao.Properties.ExternalId.columnName);

        addColumn(db, ProjectLineDao.TABLENAME, ProjectLineDao.Properties.ExternalId.columnName,
                "INTEGER");
        addUniqueIndex(db, ProjectLineDao.TABLENAME,
                ProjectLineDao.Properties.ExternalId.columnName);
    }
}
