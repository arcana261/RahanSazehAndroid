package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.migration.Migration;
import com.example.arcana.rahansazeh.model.ProjectDao;
import com.example.arcana.rahansazeh.model.ProjectLineDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/10/17.
 */

public class MigrationV2CreateProjectAndProjectLine implements Migration {
    @Override
    public Integer getVersion() {
        return 2;
    }

    @Override
    public void runMigration(Database db) {
        ProjectDao.createTable(db, false);
        ProjectLineDao.createTable(db, false);
    }
}
