
package com.hustunique.jianguo.dribile.dao;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.utils.Logger;


/**
 * Created by Dsnc on 1/9/15.
 */
public class DataProvider extends ContentProvider {
    private static final String TAG = "DataProvider";

    static final Object DBLock = new Object();

    public static final String AUTHORITY = "us.dribile.provider";

    public static final String SCHEME = "content://";

    // Users
    public static final String PATH_USERS = "/users";

    public static final Uri USERS_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_USERS);

    // AuthUser
    public static final String PATH_AUTH_USER = "/auth_user";

    public static final Uri UATH_USER_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY
            + PATH_AUTH_USER);

    // Shots
    public static final String PATH_SHOTS = "/shots";

    public static final Uri SHOTS_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_SHOTS);

    public static final String PATH_LIKES = "/likes";

    public static final Uri LIKES_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_LIKES);

    /*
     * Constants used by the Uri matcher to choose an action based on the
     * pattern of the incoming URI
     */
    private static final int USERS = 1 << 0;

    private static final int AUTHUSER = 1 << 1;

    private static final int SHOTS = 1 << 2;

    /*
     * MIME type definitions
     */
    public static final String TYPE_USERS_CONTENT = "vnd.android.cursor.dir/vnd.dribile.users";

    public static final String TYPE_AUTH_USERS_CONTENT = "vnd.android.cursor.dir/vnd.dribile.auth_user";

    public static final String TYPE_SHOTS_CONTENT = "vnd.android.cursor.dir/vnd.dribile.shots";

    public static final String TYPE_LIKES_CONTENT = "vnd.android.cursor.dir/vnd.dribile.likes";
    /**
     * A UriMatcher instance
     */
    private static final UriMatcher sUriMatcher;
    private static final int LIKES = 1 << 3;


    private static DBHelper mDBHelper;

    public static DBHelper getDBHelper() {
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(AppData.getContext());
        }

        return mDBHelper;
    }

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, "users", USERS);
        sUriMatcher.addURI(AUTHORITY, "auth_user", AUTHUSER);
        sUriMatcher.addURI(AUTHORITY, "shots", SHOTS);
        sUriMatcher.addURI(AUTHORITY, "likes", LIKES);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case AUTHUSER:
                return TYPE_AUTH_USERS_CONTENT;
            case SHOTS:
                return TYPE_SHOTS_CONTENT;
            case LIKES:
                return TYPE_LIKES_CONTENT;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    private String matchTable(Uri uri) {
        String table = null;
        switch (sUriMatcher.match(uri)) {
            case AUTHUSER:
                table = AuthUserDataHelper.AuthUserTable.TABLE_NAME;
                break;
            case SHOTS:
                table = ShotsDataHelper.ShotsTable.TABLE_NAME;
                break;
            case LIKES:
                table = LikesDataHelper.ShotsTable.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        return table;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        synchronized (DBLock) {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            String table = matchTable(uri);
            queryBuilder.setTables(table);

            SQLiteDatabase db = getDBHelper().getReadableDatabase();
            Cursor cursor = queryBuilder.query(db, // The database to
                    // queryFromDB
                    projection, // The columns to return from the queryFromDB
                    selection, // The columns for the where clause
                    selectionArgs, // The values for the where clause
                    null, // don't group the rows
                    null, // don't filter by row groups
                    sortOrder // The sort order
                    );

            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) throws SQLException {
        boolean nullId = true;
        synchronized (DBLock) {
            String table = matchTable(uri);
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            long rowId = 0;
            db.beginTransaction();
            try {
                if (nullId) {
                    rowId = db.insert(table, null, values);
                } else {
                    rowId = db.insert(table, android.provider.BaseColumns._ID, values);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Logger.e(TAG, e.getMessage());
            } finally {
                db.endTransaction();
            }
            if (rowId > 0) {
                Uri returnUri = ContentUris.withAppendedId(uri, rowId);

                getContext().getContentResolver().notifyChange(uri, null);
                return returnUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) throws SQLException {
        synchronized (DBLock) {
            String table = matchTable(uri);
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            db.beginTransaction();
            try {
                for (ContentValues contentValues : values) {
                    db.insert(table, BaseColumns._ID, contentValues);
                }
                db.setTransactionSuccessful();
                getContext().getContentResolver().notifyChange(uri, null);
                return values.length;
            } catch (Exception e) {
                Logger.e(TAG, e.getMessage());
            } finally {
                db.endTransaction();
            }
            throw new SQLException("Failed to insert row into " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        synchronized (DBLock) {
            SQLiteDatabase db = getDBHelper().getWritableDatabase();

            int count = 0;
            String table = matchTable(uri);
            db.beginTransaction();
            try {
                count = db.delete(table, selection, selectionArgs);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        synchronized (DBLock) {
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            int count;
            String table = matchTable(uri);
            db.beginTransaction();
            try {
                count = db.update(table, values, selection, selectionArgs);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
            getContext().getContentResolver().notifyChange(uri, null);

            return count;
        }
    }

    static class DBHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "dribile.db";

        private static final int VERSION = 1;

        public DBHelper(Context context) {
            super(context, DB_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            AuthUserDataHelper.AuthUserTable.TABLE.create(db);
            ShotsDataHelper.ShotsTable.TABLE.create(db);
            LikesDataHelper.ShotsTable.TABLE.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
