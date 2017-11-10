package com.example.arcana.rahansazeh.migration;

import android.content.Context;

import com.example.arcana.rahansazeh.migration.migrations.MigrationV1CreateUser;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV2CreateProjectAndProjectLine;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV3AddExternalIdToProjectAndProjectLine;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV4TruncateProjectCache;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV5CreateVehicleAndVehicleType;
import com.example.arcana.rahansazeh.model.DaoMaster;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by arcana on 11/10/17.
 */


//https://github.com/PierceZ/greenDAOMigrationExample/blob/master/src/main/java/org/greenrobot/greendao/example/DatabaseUpgradeHelper.java

public class DatabaseUpgradeHelper extends DaoMaster.OpenHelper {

    public DatabaseUpgradeHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        List<Migration> migrations = getMigrations();

        // Only run migrations past the old version
        for (Migration migration : migrations) {
            if (oldVersion < migration.getVersion()) {
                migration.runMigration(db);
            }
        }
    }

    private List<Migration> getMigrations() {
        List<Migration> migrations = new ArrayList<>();

        migrations.add(new MigrationV1CreateUser());
        migrations.add(new MigrationV2CreateProjectAndProjectLine());
        migrations.add(new MigrationV3AddExternalIdToProjectAndProjectLine());
        migrations.add(new MigrationV4TruncateProjectCache());
        migrations.add(new MigrationV5CreateVehicleAndVehicleType());

        // Sorting just to be safe, in case other people add migrations in the wrong order.
        Comparator<Migration> migrationComparator = new Comparator<Migration>() {
            @Override
            public int compare(Migration m1, Migration m2) {
                return m1.getVersion().compareTo(m2.getVersion());
            }
        };
        Collections.sort(migrations, migrationComparator);

        return migrations;
    }
}
