package com.hustunique.jianguo.driclient.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.hustunique.jianguo.driclient.app.AppData;
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
        contentValues.put(ShotsDataHelper.ShotsTable.ID, shots.getId());
        contentValues.put(ShotsDataHelper.ShotsTable.JSON, shots.getJson());
        return contentValues;
    }

    public int bulkInsert(List<Shots> shotsList) {
        int insertCount = 0;
        for (Shots shots : shotsList) {
            ContentValues values = getContentValues(shots);
            int n = update(values, ShotsDataHelper.ShotsTable.ID + "=?", new String[] {
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
                ShotsDataHelper.ShotsTable.JSON
        }, null, null, ShotsDataHelper.ShotsTable.ID + " DESC LIMIT 21");
    }

    public int deleteAll() {
        synchronized (DataProvider.DBLock) {
            DataProvider.DBHelper mDBHelper = DataProvider.getDBHelper();
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            int row = db.delete(ShotsDataHelper.ShotsTable.TABLE_NAME, null, null);
            return row;
        }
    }
    @Override
    protected Uri getContentUri() {
        return DataProvider.LIKES_CONTENT_URI;
    }
}
