package com.adreamer.clock.medol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.adreamer.clock.entity.Alarm;
import com.adreamer.clock.util.AlarmDBOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AlarmMedol implements IAlarmMedol {
    private Context mContext;

    public AlarmMedol(Context context) {
        mContext = context;
    }

    @Override
    public long insert(Alarm alarm) {
        SQLiteDatabase db = getDatabase();

        String nullColumnHack = AlarmDBOpenHelper.AlarmDatabase.Columns._ID;
        ContentValues values = new ContentValues();
        values.put(AlarmDBOpenHelper.AlarmDatabase.Columns.TIME, alarm.getTime());
        values.put(AlarmDBOpenHelper.AlarmDatabase.Columns.REPEATED, alarm.isRepeated());
        values.put(AlarmDBOpenHelper.AlarmDatabase.Columns.ENABLE, alarm.isEnable());

        long id = db.insert(AlarmDBOpenHelper.AlarmDatabase.TABLE_NAME, nullColumnHack, values);
        db.close();

        return id;
    }

    @Override
    public int delete(Alarm alarm) {
        SQLiteDatabase db = getDatabase();

        String table = AlarmDBOpenHelper.AlarmDatabase.TABLE_NAME;
        String whereClause = AlarmDBOpenHelper.AlarmDatabase.Columns._ID + "=?";
        String[] whereArgs = {alarm.getId() + ""};

        int cols = db.delete(table, whereClause, whereArgs);
        db.close();

        return cols;
    }

    @Override
    public int update(Alarm alarm) {
        SQLiteDatabase db = getDatabase();
        String table = AlarmDBOpenHelper.AlarmDatabase.TABLE_NAME;
        ContentValues values = new ContentValues();
        values.put(AlarmDBOpenHelper.AlarmDatabase.Columns.TIME, alarm.getTime());
        values.put(AlarmDBOpenHelper.AlarmDatabase.Columns.REPEATED, alarm.isRepeated());
        values.put(AlarmDBOpenHelper.AlarmDatabase.Columns.ENABLE, alarm.isEnable());
        String whereClause = AlarmDBOpenHelper.AlarmDatabase.Columns._ID + "=?";
        String[] whereArgs = {alarm.getId() + ""};

        int cols = db.update(table, values, whereClause, whereArgs);
        db.close();

        return cols;
    }

    @Override
    public List<Alarm> query(String selection, String[] selectionArgs) {
        List<Alarm> alarms = new ArrayList<>();
        SQLiteDatabase db = getDatabase();

        String table = AlarmDBOpenHelper.AlarmDatabase.TABLE_NAME;
        String[] columns = {
                AlarmDBOpenHelper.AlarmDatabase.Columns._ID,
                AlarmDBOpenHelper.AlarmDatabase.Columns.TIME,
                AlarmDBOpenHelper.AlarmDatabase.Columns.REPEATED,
                AlarmDBOpenHelper.AlarmDatabase.Columns.ENABLE
        };
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Alarm alarm = new Alarm();
            alarm.setId(cursor.getLong(0));
            alarm.setTime(cursor.getInt(1));
            alarm.setRepeated(cursor.getInt(2) != 0);
            alarm.setEnable(cursor.getInt(3) != 0);
            alarms.add(alarm);
        }

        db.close();

        return alarms;
    }

    private SQLiteDatabase getDatabase() {
        AlarmDBOpenHelper dbOpenHelper = new AlarmDBOpenHelper(mContext);
        return dbOpenHelper.getWritableDatabase();
    }
}
