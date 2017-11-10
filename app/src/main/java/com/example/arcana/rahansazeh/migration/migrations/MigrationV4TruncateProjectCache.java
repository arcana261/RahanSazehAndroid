package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.model.ProjectDao;
import com.example.arcana.rahansazeh.model.ProjectLineDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/10/17.
 */

public class MigrationV4TruncateProjectCache extends BaseMigration {
    @Override
    public Integer getVersion() {
        return 4;
    }

    @Override
    public void runMigration(Database db) {
        truncate(db, ProjectDao.TABLENAME);
        truncate(db, ProjectLineDao.TABLENAME);
    }
}
