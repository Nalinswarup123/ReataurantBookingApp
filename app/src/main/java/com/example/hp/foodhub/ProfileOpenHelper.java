package com.example.hp.foodhub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfileOpenHelper extends SQLiteOpenHelper {

    public static final String username = "_uname", profile_name= "_pname",
            mobno="_mob",city="_city",country="_country";

    public ProfileOpenHelper(Context contex) {
        super(contex, "ProfileDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table UserProfile (_uname TEXT primary key, " +
                "_pname TEXT, _mob TEXT, _city TEXT, _country TEXT)");
    }

    public long insertItem(String... s){
        ContentValues cv = new ContentValues();
        cv.put(username, s[0]);
        cv.put(profile_name, s[1]);
        cv.put(mobno, s[2]);
        cv.put(city, s[3]);
        cv.put(country, s[4]);

        return this.getWritableDatabase().insert("UserProfile", null, cv);
    }
    public Cursor getData(){
        return this.getReadableDatabase().rawQuery("Select * from "+"UserProfile",null );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
