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

/**
 * Created by Dsnc on 1/5/15.
 */
public class Column {
    public static enum Constraint {
        UNIQUE("UNIQUE"), NOT("NOT"), NULL("NULL"), CHECK("CHECK"), FOREIGN_KEY("FOREIGN KEY"), PRIMARY_KEY(
                "PRIMARY KEY");

        private String value;

        private Constraint(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static enum DataType {
        NULL, INTEGER, REAL, TEXT, BLOB
    }

    private String mColumnName;

    private Constraint mConstraint;

    private DataType mDataType;

    public Column(String columnName, Constraint constraint, DataType dataType) {
        mColumnName = columnName;
        mConstraint = constraint;
        mDataType = dataType;
    }

    public String getColumnName() {
        return mColumnName;
    }

    public Constraint getConstraint() {
        return mConstraint;
    }

    public DataType getDataType() {
        return mDataType;
    }
}
