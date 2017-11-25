package com.example.arcana.rahansazeh.migration.migrations;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.arcana.rahansazeh.migration.Migration;
import com.gurkashi.lava.lambdas.Predicate;
import com.gurkashi.lava.queries.stracture.Queriable;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arcana on 11/10/17.
 */

public abstract class BaseMigration implements Migration {
    protected static class ColumnDescription {
        public String columnName;
        public String columnType;
        public boolean notNull;
        public boolean isPrimaryKey;
    }

    protected List<ColumnDescription> describeTable(Database db, String tableName) {
        Cursor cursor = db.rawQuery("PRAGMA table_info(\"" + tableName + "\")", new String[0]);

        String[] columnNames = cursor.getColumnNames();
        int nameColumn = -1;
        int typeColumn = -1;
        int notNullColumn = -1;
        int primaryKeyColumn = -1;

        ArrayList<ColumnDescription> result = new ArrayList<>();

        for (int i = 0; i < columnNames.length; i++) {
            if ("name".equals(columnNames[i])) {
                nameColumn = i;
            }
            else if ("type".equals(columnNames[i])) {
                typeColumn = i;
            }
            else if ("notnull".equals(columnNames[i])) {
                notNullColumn = i;
            }
            else if ("pk".equals(columnNames[i])) {
                primaryKeyColumn = i;
            }
        }

        while (cursor.moveToNext()) {
            ColumnDescription item = new ColumnDescription();

            if (nameColumn >= 0) {
                item.columnName = cursor.getString(nameColumn);
            }
            else {
                item.columnName = "";
            }

            if (typeColumn >= 0) {
                item.columnType = cursor.getString(typeColumn);
            }
            else {
                item.columnType = "";
            }

            if (notNullColumn >= 0) {
                item.notNull = cursor.getInt(notNullColumn) != 0;
            }
            else {
                item.notNull = false;
            }

            if (primaryKeyColumn >= 0) {
                item.isPrimaryKey = cursor.getInt(primaryKeyColumn) != 0;
            }
            else {
                item.isPrimaryKey = false;
            }

            result.add(item);
        }

        return result;
    }

    protected boolean hasColumn(Database db, String tableName, final String columnName) {
        return Queriable.create(describeTable(db, tableName))
                .exists(new Predicate<ColumnDescription>() {
                    @Override
                    public boolean predict(ColumnDescription columnDescription) {
                        return columnName.equals(columnDescription.columnName);
                    }
                }).execute();
    }

    protected void addColumn(Database db, String tableName, String columnName, String columnType) {
        db.execSQL("ALTER TABLE [" + tableName + "] ADD COLUMN [" +
                columnName + "] " + columnType);
    }

    protected void addColumnIfNotExists(Database db, String tableName, String columnName, String columnType) {
        if (!hasColumn(db, tableName, columnName)) {
            addColumn(db, tableName, columnName, columnType);
        }
    }

    protected void addColumnNotNull(Database db, String tableName, String columnName,
                                    String columnType, String defaultValue) {
        db.execSQL("ALTER TABLE [" + tableName + "] ADD COLUMN [" +
                columnName + "] " + columnType + " NOT NULL DEFAULT " + defaultValue);
    }

    protected void addColumnNotNullIfNotExists(Database db, String tableName, String columnName,
                                    String columnType, String defaultValue) {
        if (!hasColumn(db, tableName, columnName)) {
            addColumnNotNull(db, tableName, columnName, columnType, defaultValue);
        }
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

    protected void renameTable(Database db, String oldTableName, String newTableName) {
        db.execSQL("ALTER TABLE [" + oldTableName + "] RENAME TO [" + newTableName + "]");
    }

    protected void dropTable(Database db, String tableName) {
        db.execSQL("DROP TABLE [" + tableName + "]");
    }

    protected void copyTable(Database db, String fromTable, String toTable, String[] fromColumns, String[] toColumns) {
        if (fromColumns.length != toColumns.length) {
            throw new IllegalArgumentException("fromColumns.length != toColumns.length");
        }

        StringBuilder builder = new StringBuilder();

        builder.append("INSERT INTO [").append(toTable).append("] (");

        for (int i = 0; i < toColumns.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }

            builder.append("[").append(toColumns[i]).append("]");
        }

        builder.append(") SELECT ");

        for (int i = 0; i < fromColumns.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }

            builder.append("[").append(fromColumns[i]).append("]");
        }

        builder.append(" FROM ").append("[").append(fromTable).append("]");

        db.execSQL(builder.toString());
    }
}
