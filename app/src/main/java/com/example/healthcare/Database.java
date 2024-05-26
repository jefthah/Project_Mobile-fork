package com.example.healthcare;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry1 = "create table users(username text, email text, password text)";
        sqLiteDatabase.execSQL(qry1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void register(String username, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put("username", username); // Corrected here
        cv.put("email", email);
        cv.put("password", password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }


    public int login(String username, String password){
        int result = 0;
        String str [] =new String[2];
        str [0] = username;
        str [1] = password;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from users where username=? and password=?", str);
        if(c.moveToFirst()){
            result=1;
        }
        return result;
    }
    public void updateProfile(String username, String newEmail, String newPassword) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("email", newEmail);
            cv.put("password", newPassword);
            db.update("users", cv, "username=?", new String[]{username});
            db.close();
        } catch (Exception e) {
            Log.e(TAG, "updateProfile: ", e);
        }
    }

    public void deleteAccount(String username) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.delete("users", "username=?", new String[]{username});
            db.close();
        } catch (Exception e) {
            Log.e(TAG, "deleteAccount: ", e);
        }
    }

}