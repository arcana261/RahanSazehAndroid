package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.ProjectLineDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/25/17.
 */

public class MigrationV19AddSortCriteriaToProjectLine extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 19;
    }

    @Override
    public void runMigration(Database db) {
        ProjectLineDao.dropTable(db, true);
        ProjectLineDao.createTable(db, false);
    }
}
