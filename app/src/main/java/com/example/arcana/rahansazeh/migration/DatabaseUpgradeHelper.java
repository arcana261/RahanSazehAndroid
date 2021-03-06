package com.example.arcana.rahansazeh.migration;

import android.content.Context;

import com.example.arcana.rahansazeh.migration.migrations.MigrationV10AddTerminalToOutgoingVehicle;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV11AddHasSelectedTerminalInPassengerRecordEntry;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV12RecreateAllTables;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV13AddClientIdField;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV14AddProjectToVehicle;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV15RemoveProjectLineFromVehicle;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV16AddUpdateIdToVehicle;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV17AddSupportForVehicleUpdates;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV18AddIndexToVehicle;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV19AddSortCriteriaToProjectLine;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV1CreateUser;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV2CreateProjectAndProjectLine;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV3AddExternalIdToProjectAndProjectLine;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV4TruncateProjectCache;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV5CreateVehicleAndVehicleType;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV6CreateOutgoingRecordTable;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV7CreateOutgoingPassengerRecord;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV8AddLoadAndUnloadToOutgoingVehicle;
import com.example.arcana.rahansazeh.migration.migrations.MigrationV9AddPrimaryKeyToOutgoingPassenger;
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
        migrations.add(new MigrationV6CreateOutgoingRecordTable());
        migrations.add(new MigrationV7CreateOutgoingPassengerRecord());
        migrations.add(new MigrationV8AddLoadAndUnloadToOutgoingVehicle());
        migrations.add(new MigrationV9AddPrimaryKeyToOutgoingPassenger());
        migrations.add(new MigrationV10AddTerminalToOutgoingVehicle());
        migrations.add(new MigrationV11AddHasSelectedTerminalInPassengerRecordEntry());
        migrations.add(new MigrationV12RecreateAllTables());
        migrations.add(new MigrationV13AddClientIdField());
        migrations.add(new MigrationV14AddProjectToVehicle());
        migrations.add(new MigrationV15RemoveProjectLineFromVehicle());
        migrations.add(new MigrationV16AddUpdateIdToVehicle());
        migrations.add(new MigrationV17AddSupportForVehicleUpdates());
        migrations.add(new MigrationV18AddIndexToVehicle());
        migrations.add(new MigrationV19AddSortCriteriaToProjectLine());

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
