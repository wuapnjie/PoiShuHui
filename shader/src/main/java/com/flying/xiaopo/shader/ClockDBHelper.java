package com.flying.xiaopo.shader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.flying.xiaopo.shader.Bean.TipBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2015/6/13.
 */
public class ClockDBHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "xiaopo";
    public static String DB_TABLE_NAME = "remind_table";
    public static String COL_ID = "id";
    public static String COL_TITLE = "title";
    public static String COL_DATE = "date";
    public static String COL_TIME = "time";

    public ClockDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public ClockDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_sql = "CREATE TABLE " + DB_TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_TITLE + " TEXT," +
                COL_DATE + " TEXT," +
                COL_TIME + " INTEGER" + ")";
        db.execSQL(create_table_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) {
            return;
        }
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
        onCreate(db);
    }

    public int addRemind(TipBean bean){
        SQLiteDatabase dbWriter = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE,bean.getTitle());
        cv.put(COL_DATE,bean.getDate());
        cv.put(COL_TIME, bean.getTime());
        long ID = dbWriter.insert(DB_TABLE_NAME,null,cv);
        return (int) ID;
    }

    public TipBean getRemind(int id) {
        SQLiteDatabase dbReader = this.getReadableDatabase();
        Cursor cursor = dbReader.query(DB_TABLE_NAME,
                new String[]{COL_ID, COL_TITLE, COL_DATE, COL_TIME},
                COL_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        TipBean bean = new TipBean(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        return bean;
    }

    public List<TipBean> getAllBeans(){
        List<TipBean> beans = new ArrayList<>();
        SQLiteDatabase dbReader = this.getReadableDatabase();
        Cursor cursor= dbReader.query(DB_TABLE_NAME, new String[]{COL_ID, COL_TITLE, COL_DATE, COL_TIME}, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                TipBean bean = new TipBean(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                beans.add(bean);
            }while (cursor.moveToNext());
        }

        cursor.close();
        return beans;
    }

    public int updateReminder(TipBean bean,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(COL_ID,id);
        values.put(COL_TITLE,bean.getTitle());
        values.put(COL_DATE,bean.getDate());
        values.put(COL_TIME, bean.getTime());

        return db.update(DB_TABLE_NAME,values,COL_ID+"=?",new String[]{String.valueOf(id)});
    }

    public void deleteReminder(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE_NAME,"ID = ?",new String[]{String.valueOf(id)});
        db.close();
    }

}
