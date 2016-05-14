package com.hustunique.jianguo.driclient.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.dao.sql.BaseColumns;
import com.hustunique.jianguo.driclient.dao.sql.Column;
import com.hustunique.jianguo.driclient.dao.sql.SQLiteTable;
import com.hustunique.jianguo.driclient.models.Shots;

import java.util.List;

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
        contentValues.put(ShotsTable.JSON, shots.getJson());
        return contentValues;
    }

    public int bulkInsert(List<Shots> shotsList) {
        int insertCount = 0;
        for (Shots shots : shotsList) {
            ContentValues values = getContentValues(shots);
            int n = update(values, ShotsTable.ID + "=?", new String[] {
                    shots.getId()
            });
            if (n == 0) {
                insert(values);
                insertCount++;
            }
        }
        return insertCount;
    }

    public Cursor getList() {
        return getList(new String[] {
                ShotsTable.JSON
        }, null, null, ShotsTable.ID + " DESC LIMIT 21");
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

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME).addColumn(ID,
                Column.DataType.TEXT).addColumn(JSON, Column.DataType.TEXT);
    }
}
