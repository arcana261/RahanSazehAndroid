package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.migration.Migration;
import com.example.arcana.rahansazeh.model.UserDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/10/17.
 */

public class MigrationV1CreateUser implements Migration {
    @Override
    public Integer getVersion() {
        return 1;
    }

    @Override
    public void runMigration(Database db) {
        UserDao.createTable(db, false);
    }
}
