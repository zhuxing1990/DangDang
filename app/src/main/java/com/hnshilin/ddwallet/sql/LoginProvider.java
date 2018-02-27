package com.hnshilin.ddwallet.sql;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.hnshilin.ddwallet.log.LogUtil;


/**
 * Created by zhuxi on 2017/7/28.
 */
public class LoginProvider extends ContentProvider{
    private static final String TAG = "LoginProvider";
    private final static String AUTHORITH = "com.hnshilin.ddwallet.provider.login";
    private final static String PATH = "/login";
    private final static String PATHS = "/login/#";
    private String TABLE_NAME = "login";
    private final static UriMatcher mUriMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    private static final int CODE_DIR = 1;
    private static final int CODE_ITEM = 2;
    static {
        mUriMatcher.addURI(AUTHORITH, PATH, CODE_DIR);
        mUriMatcher.addURI(AUTHORITH, PATHS, CODE_ITEM);
    }
    private LoginSqlite dbHelper;
    private SQLiteDatabase db;

    private static final String LOGIN_TIME = "login_time";
    private static final String USER_NAME = "user_name";
//    private static final String PASSWORD ="password";
    @Override
    public boolean onCreate() {
        dbHelper = new LoginSqlite(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        db = dbHelper.getWritableDatabase();
        switch (mUriMatcher.match(uri)) {
            case 1:
                String sql = "";
                if (selectionArgs != null && selectionArgs.length != 0) {
                    sql = "select * from "+TABLE_NAME+" where "+USER_NAME+" = '" +selectionArgs[0]+"'" ;
                    sql = sql + " order by "+LOGIN_TIME+" desc";
                    cursor = db.rawQuery(sql, null);
                }
//                else{
//                sql = "select * from " + TABLE_NAME;
//                }
                break;

            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case CODE_DIR:
                return "vnd.android.cursor.dir/" + TABLE_NAME;
            case CODE_ITEM:
                return "vnd.android.cursor.item/" + TABLE_NAME;
            default:
                throw new IllegalArgumentException("异常参数");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (values!=null) {
            db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select "+USER_NAME+" from " + TABLE_NAME
                    + " where "+USER_NAME+" = '" + values.get(USER_NAME) + "'", null);
            try {
                // db.query(true, TABLE_NAME, new String[] {
                // "body", "user_id", "create_time" }, null, null, "user_id", null,
                // null, null, null);
                if (cursor.moveToNext()) {// 有下一个 ，更新
                    String userID = values.get(USER_NAME).toString();
                    int update = db.update(TABLE_NAME, values, USER_NAME + "=?", new String[]{userID});
                    LogUtil.i(TAG, "insert: update:"+update);
                } else {// 否则 插入数据
                    switch (mUriMatcher.match(uri)) {
                        case 1:
                            long insert = db.insert(TABLE_NAME, null, values);
                            LogUtil.i(TAG, "insert: insert:"+insert);
                            break;
                    }
                }
                // db.execSQL("delete from groupinfo where rowid not in(select max(rowid) from groupinfo group by user_id)");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (cursor!=null)
                    cursor.close();
            }

        }
        return uri;
    }
    @Nullable
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int numValues = 0;
        db.beginTransaction(); //开始事务
        try {
            //数据库操作
            numValues = values.length;
            for (int i = 0; i < numValues; i++) {
                insert(uri, values[i]);
            }
            db.setTransactionSuccessful(); //别忘了这句 Commit
        } finally {
            db.endTransaction(); //结束事务
        }
        return numValues;
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int num = 0;
        db = dbHelper.getWritableDatabase();
        num = db.delete(TABLE_NAME,selection,selectionArgs);
        return num;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int number = 0;
        db = dbHelper.getWritableDatabase();
//        switch (mUriMatcher.match(uri)) {
//            case 1:
        number = db.update(TABLE_NAME, values, selection, selectionArgs);
//                break;
//            case 2:
//                long id = ContentUris.parseId(uri);
//                selection = (selection != null || "".equals(selection.trim()) ? USER_ID
//                        + "=" + id
//                        : selection + "and" + USER_ID + "=" + id);
//                number = db.update(TABLE_NAME, values, selection, selectionArgs);
//        }
        return number;
    }
}
