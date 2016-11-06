/*
 * Copyright (c) 2016.  TedaLIEz <aliezted@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hustunique.jianguo.dribile.dao.sql;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by JianGuo on 4/1/16.
 * SQL Table
 */
public class SQLiteTable {
    String mTableName;

    ArrayList<Column> mColumnsDefinitions = new ArrayList<Column>();

    public String getTableName() {
        return mTableName;
    }

    /**
     * Add primary key automatically BaseColumns._ID
     *
     * @param tableName
     */
    public SQLiteTable(String tableName) {
        mTableName = tableName;
        mColumnsDefinitions.add(new Column(android.provider.BaseColumns._ID, Column.Constraint.PRIMARY_KEY,
                Column.DataType.INTEGER));
    }

    public SQLiteTable addColumn(Column columnsDefinition) {
        mColumnsDefinitions.add(columnsDefinition);
        return this;
    }

    public SQLiteTable addColumn(String columnName, Column.DataType dataType) {
        mColumnsDefinitions.add(new Column(columnName, null, dataType));
        return this;
    }

    public SQLiteTable addColumn(String columnName, Column.Constraint constraint, Column.DataType dataType) {
        mColumnsDefinitions.add(new Column(columnName, constraint, dataType));
        return this;
    }

    public void create(SQLiteDatabase db) {
        String formatter = " %s";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXISTS ");
        stringBuilder.append(mTableName);
        stringBuilder.append("(");
        int columnCount = mColumnsDefinitions.size();
        int index = 0;
        for (Column columnsDefinition : mColumnsDefinitions) {
            stringBuilder.append(columnsDefinition.getColumnName()).append(
                    String.format(formatter, columnsDefinition.getDataType().name()));
            Column.Constraint constraint = columnsDefinition.getConstraint();

            if (constraint != null) {
                stringBuilder.append(String.format(formatter, constraint.toString()));
            }
            if (index < columnCount - 1) {
                stringBuilder.append(",");
            }
            index++;
        }
        stringBuilder.append(");");
        db.execSQL(stringBuilder.toString());
    }

    public void delete(final SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + mTableName);
    }
}
