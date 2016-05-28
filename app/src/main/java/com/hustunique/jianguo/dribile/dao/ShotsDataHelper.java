package com.hustunique.jianguo.dribile.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.dao.sql.BaseColumns;
import com.hustunique.jianguo.dribile.dao.sql.Column;
import com.hustunique.jianguo.dribile.dao.sql.SQLiteTable;
import com.hustunique.jianguo.dribile.models.Shots;

/**
 * Created by JianGuo on 4/8/16.
 * Data Helper for {@link Shots}
 */
public class ShotsDataHelper extends BasicDataHelper {

    public ShotsDataHelper() {
        super(AppData.getContext());
    }

    private ContentValues getContentValues(Shots shots) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShotsTable.ID, shots.getId());
        contentValues.put(ShotsTable.VIEWS, Integer.valueOf(shots.getViews_count()));
        contentValues.put(ShotsTable.COMMENTS, Integer.valueOf(shots.getComments_count()));
        contentValues.put(ShotsTable.LIKES, Integer.valueOf(shots.getLikes_count()));
        contentValues.put(ShotsTable.JSON, shots.getJson());
        return contentValues;
    }


    public boolean insert(Shots shots) {
        ContentValues contentValues = getContentValues(shots);
        insert(contentValues);
        return true;
    }

    //TODO: strange when getting data from database.
    public Cursor getList() {
        synchronized (DataProvider.DBLock) {
            DataProvider.DBHelper mDBHelper = DataProvider.getDBHelper();
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            return db.query(ShotsTable.TABLE_NAME, new String[] {ShotsTable.JSON}, null ,null, null, null, ShotsTable.VIEWS + " DESC");
        }
//        return getList(new String[]{
//                ShotsTable.JSON
//        }, null, null, ShotsTable.VIEWS + " DESC");
    }

    public int deleteAll() {
        synchronized (DataProvider.DBLock) {
            DataProvider.DBHelper mDBHelper = DataProvider.getDBHelper();
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            int row = db.delete(ShotsTable.TABLE_NAME, null, null);
            return row;
        }
    }

    @Override
    protected Uri getContentUri() {
        return DataProvider.SHOTS_CONTENT_URI;
    }


    public static class ShotsTable implements BaseColumns {
        public static final String TABLE_NAME = "shots";

        public static final String ID = "id";

        public static final SQLiteTable TABLE
                = new SQLiteTable(TABLE_NAME)
                .addColumn(ID, Column.DataType.TEXT)
                .addColumn(VIEWS, Column.DataType.INTEGER)
                .addColumn(COMMENTS, Column.DataType.INTEGER)
                .addColumn(LIKES, Column.DataType.INTEGER)
                .addColumn(JSON, Column.DataType.TEXT);
    }
}
