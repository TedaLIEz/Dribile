package com.hustunique.jianguo.driclient.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.dao.sql.BaseColumns;
import com.hustunique.jianguo.driclient.dao.sql.Column;
import com.hustunique.jianguo.driclient.dao.sql.SQLiteTable;
import com.hustunique.jianguo.driclient.models.Likes;
import com.hustunique.jianguo.driclient.models.Shots;

import java.util.List;

/**
 * Created by JianGuo on 5/8/16.
 * DataHelper for caching authUser's likes
 */
public class LikesDataHelper extends BasicDataHelper {

    public LikesDataHelper() {
        super(AppData.getContext());
    }

    private ContentValues getContentValues(Shots shots) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LikesDataHelper.ShotsTable.ID, shots.getId());
        contentValues.put(LikesDataHelper.ShotsTable.JSON, shots.getJson());
        return contentValues;
    }



    public boolean insert(Shots shots) {
        ContentValues contentValues = getContentValues(shots);
        insert(contentValues);
        return true;
    }

    public Cursor getList() {
        return getList(new String[] {
                LikesDataHelper.ShotsTable.JSON
        }, null, null, LikesDataHelper.ShotsTable.ID + " DESC LIMIT 21");
    }

    public int deleteAll() {
        synchronized (DataProvider.DBLock) {
            DataProvider.DBHelper mDBHelper = DataProvider.getDBHelper();
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            int row = db.delete(LikesDataHelper.ShotsTable.TABLE_NAME, null, null);
            return row;
        }
    }
    @Override
    protected Uri getContentUri() {
        return DataProvider.LIKES_CONTENT_URI;
    }


    public static class ShotsTable implements BaseColumns {
        public static final String TABLE_NAME = "likes";

        public static final String ID = "id";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME).addColumn(ID,
                Column.DataType.TEXT).addColumn(JSON, Column.DataType.TEXT);
    }
}
