package com.example.project_final_ver_01.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.database.entities.UserYogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaCourse;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "yoga_class_platform_ver_02_db";


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

    //Users
    public boolean addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(User.COLUMN_EMAIL, user.getEmail());
        cv.put(User.COLUMN_ROLE, user.getRole());
        cv.put(User.COLUMN_PHONE_NUMBER, user.getPhone_number());
        cv.put(User.COLUMN_NAME, user.getUser_name());
        cv.put(User.COLUMN_DESCRIPTION, user.getDescription());

        long insert = db.insert(User.TABLE_NAME, null, cv);
        return insert != -1;
    }
    public List<User> getAllUser(String role_user){
        List<User> listUser = new ArrayList<>();
        String queryString = User.GET_USER_LIST;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do{
                int user_id = cursor.getInt(0);
                String email = cursor.getString(1);
                String role = cursor.getString(2);
                String phone_number = cursor.getString(3);
                String user_name = cursor.getString(4);
                String description = cursor.getString(5);
                if(!role.equals(role_user)) continue;
                User newUser = new User(user_id, email, role, phone_number, user_name, description);
                listUser.add(newUser);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listUser;
    }

    //Courses
    public boolean addCourse(YogaCourse yogaCourse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(YogaCourse.COLUMN_NAME, yogaCourse.getCourse_name());
        cv.put(YogaCourse.COLUMN_DAY_OF_THE_WEEK, yogaCourse.getDay_of_the_week());
        cv.put(YogaCourse.COLUMN_TIME_OF_COURSE, yogaCourse.getTime_of_course());
        cv.put(YogaCourse.COLUMN_CAPACITY, yogaCourse.getCapacity());
        cv.put(YogaCourse.COLUMN_DURATION, yogaCourse.getDuration());
        cv.put(YogaCourse.COLUMN_TYPE_OF_CLASS, yogaCourse.getType_of_class());
        cv.put(YogaCourse.COLUMN_PRICE_PER_CLASS, yogaCourse.getPrice_per_class());
        cv.put(YogaCourse.COLUMN_DESCRIPTION, yogaCourse.getDescriptions());
        long insert = db.insert(YogaCourse.TABLE_NAME, null, cv);
        return insert != -1;
    }
    public List<YogaCourse> getALlYogaCourse() {
        List<YogaCourse> coursesList = new ArrayList<>();

        String queryString = YogaCourse.GET_COURSE_LIST;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int yoga_course_id = cursor.getInt(0);
                String course_name = cursor.getString(1);
                String day_of_the_week = cursor.getString(2);
                String time_of_course = cursor.getString(3);
                int capacity = cursor.getInt(4);
                int duration = cursor.getInt(5);
                String type_of_course = cursor.getString(6);
                double price_per_class = cursor.getDouble(7);
                String description = cursor.getString(8);
                YogaCourse yogaCourse = new YogaCourse(yoga_course_id, course_name, day_of_the_week, time_of_course, capacity, duration, type_of_course, price_per_class, description);
                coursesList.add(yogaCourse);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return coursesList;
    }
    //Class Instances
}