package com.example.hp.foodhub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookingOpenHelper extends SQLiteOpenHelper {

    public static final String bookingID = "_ID", username = "_uname", rest = "_res", city="_city",amount="_amount",
                                    tabpos = "_tabpos", tabType = "_tabType", seldate = "_date", selTime = "_time";

    public BookingOpenHelper(Context contex) {
        super(contex, "BookingDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table BookingProfile (_ID TEXT primary key, " +
                "_uname TEXT, _res TEXT, _city TEXT, _tabpos TEXT, _tabType TEXT, _date TEXT, _time TEXT, _amount TEXT)");
    }

    public long insertItem(long bookinId,int tableCharge, String uName, String[] s){
        ContentValues cv = new ContentValues();
        cv.put(bookingID,bookinId);
        cv.put(username, uName);
        cv.put(rest, s[0]);
        cv.put(city, s[1]);
        cv.put(tabpos, s[2]);
        cv.put(tabType, s[3]);
        cv.put(seldate, s[4]);
        cv.put(selTime, s[5]);
        cv.put(amount,tableCharge+"");
        return this.getWritableDatabase().insert("BookingProfile", null, cv);
    }
    public Cursor getData(){
        return this.getReadableDatabase().rawQuery("Select * from "+"BookingProfile"+" order by"+
                " _ID desc",null );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
