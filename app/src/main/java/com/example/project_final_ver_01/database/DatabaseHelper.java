package com.example.project_final_ver_01.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.database.entities.UserYogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaCourse;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "yoga_class_platform_db";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE);
        db.execSQL(YogaClassInstance.CREATE_TABLE);
        db.execSQL(YogaCourse.CREATE_TABLE);
        db.execSQL(UserYogaClassInstance.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + YogaClassInstance.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + YogaCourse.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserYogaClassInstance.TABLE_NAME);

        onCreate(db);
    }
}