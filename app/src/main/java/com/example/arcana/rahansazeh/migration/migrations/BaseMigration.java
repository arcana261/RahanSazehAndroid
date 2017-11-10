package com.example.arcana.rahansazeh.migration.migrations;

import com.example.arcana.rahansazeh.migration.Migration;

import org.greenrobot.greendao.database.Database;

/**
 * Created by arcana on 11/10/17.
 */

public abstract class BaseMigration implements Migration {
    protected void addColumn(Database db, String tableName, String columnName, String columnType) {
        db.execSQL("ALTER TABLE [" + tableName + "] ADD COLUMN [" +
                columnName + "] " + columnType);
    }

    protected void addIndex(Database db, String tableName, boolean unique, String... columns) {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE ");
        if (unique) {
            builder.append("UNIQUE ");
        }
        builder.append("INDEX [IDX_");
        builder.append(tableName);
        for (String column : columns) {
            builder.append("_");
            builder.append(column);
        }
        builder.append("] ON [");
        builder.append(tableName);
        builder.append("] (");
        boolean first = true;
        for (String column : columns) {
            if (first) {
                first = false;
            }
            else {
                builder.append(", ");
            }
            builder.append("[");
            builder.append(column);
            builder.append("] ASC");
        }
        builder.append(")");

        String query = builder.toString();

        db.execSQL(query);
    }

    protected void addIndex(Database db, String tableName, String... columns) {
        addIndex(db, tableName, false, columns);
    }

    protected void addUniqueIndex(Database db, String tableName, String... columns) {
        addIndex(db, tableName, true, columns);
    }

    protected void truncate(Database db, String tableName) {
        db.execSQL("DELETE FROM [" + tableName + "]");
    }
}
