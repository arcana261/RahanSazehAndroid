package com.example.arcana.rahansazeh.migration;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/10/17.
 */

public interface Migration {
    Integer getVersion();
    void runMigration(Database db);
}
