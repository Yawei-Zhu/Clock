package com.adreamer.clock.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDBOpenHelper extends SQLiteOpenHelper {

    public AlarmDBOpenHelper(Context context) {
        super(context, "alarm.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + AlarmDatabase.TABLE_NAME + " (" +
                AlarmDatabase.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AlarmDatabase.Columns.TIME + " INTEGER NOT NULL, " +
                AlarmDatabase.Columns.REPEATED + " BOOLEAN, " +
                AlarmDatabase.Columns.ENABLE + " BOOLEAN)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO
    }

    public static class AlarmDatabase {

        public static final String TABLE_NAME = "alarms";

        public static final class Columns {
            public static final String _ID = "_id";
            public static final String TIME = "time";
            public static final String REPEATED = "repeated";
            public static final String ENABLE = "enable";
        }
    }

}
