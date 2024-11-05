package com.example.project_final_ver_01.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
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
        db.execSQL(YogaCourse.CREATE_TABLE);
        db.execSQL(YogaClassInstance.CREATE_TABLE);
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
    public boolean addUser(@NonNull User user){
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
    public boolean updateUser(@NonNull User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(User.COLUMN_EMAIL, user.getEmail());
        cv.put(User.COLUMN_ROLE, user.getRole());
        cv.put(User.COLUMN_PHONE_NUMBER, user.getPhone_number());
        cv.put(User.COLUMN_NAME, user.getUser_name());
        cv.put(User.COLUMN_DESCRIPTION, user.getDescription());

        long update = db.update(User.TABLE_NAME, cv, User.COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
        return update > 0;
    }
    public List<User> getAllUser(){
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
                User newUser = new User(user_id, email, role, phone_number, user_name, description);
                listUser.add(newUser);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listUser;
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
    public boolean deleteUser (@NonNull User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete(User.TABLE_NAME, User.COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
        if(delete == 0) return false;
        db.close();
        return true;
    }

    //Courses
    public boolean addCourse(@NonNull YogaCourse yogaCourse) {
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
    public boolean updateCourse(@NonNull YogaCourse yogaCourse) {
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

        int update = db.update(YogaCourse.TABLE_NAME, cv, YogaCourse.COLUMN_ID + " = ?", new String[]{String.valueOf(yogaCourse.getId())});
        return update > 0;
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
    public boolean deleteCourse(@NonNull YogaCourse yogaCourse) {
        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete(YogaCourse.TABLE_NAME, YogaCourse.COLUMN_ID + " = ?", new String[]{String.valueOf(yogaCourse.getId())});
        if(delete == 0) return false;
        db.close();
        return true;
    }

    //Class Instances
    public boolean addClassInstance(@NonNull YogaClassInstance yogaClassInstance) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(YogaClassInstance.COLUMN_YOGA_COURSE_ID, yogaClassInstance.getYoga_course_id());
        cv.put(YogaClassInstance.COLUMN_SCHEDULE, yogaClassInstance.getSchedule());
        cv.put(YogaClassInstance.COLUMN_DESCRIPTION, yogaClassInstance.getDescription());
        long insert = db.insert(YogaClassInstance.TABLE_NAME, null, cv);
        return insert != -1;
    }
    public boolean updateClassInstance(@NonNull YogaClassInstance yogaClassInstance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(YogaClassInstance.COLUMN_YOGA_COURSE_ID, yogaClassInstance.getYoga_course_id());
        cv.put(YogaClassInstance.COLUMN_SCHEDULE, yogaClassInstance.getSchedule());
        cv.put(YogaClassInstance.COLUMN_DESCRIPTION, yogaClassInstance.getDescription());

        long update = db.update(YogaClassInstance.TABLE_NAME, cv, YogaClassInstance.COLUMN_ID + " = ?", new String[]{String.valueOf(yogaClassInstance.getYoga_class_instance_id())});
        return update > 0;
    }
    public List<YogaClassInstance> getAllYogaClassInstance(){
        List<YogaClassInstance> classList = new ArrayList<>();

        String queryString = YogaClassInstance.GET_CLASS_LIST;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int yoga_class_instance = cursor.getInt(0);
                int yoga_course_id = cursor.getInt(1);
                String schedule = cursor.getString(2);
                String description = cursor.getString(3);
                YogaClassInstance yogaClassInstance = new YogaClassInstance(yoga_class_instance, yoga_course_id, schedule, description);
                classList.add(yogaClassInstance);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return classList;
    }
    public YogaClassInstance getYogaClassInstanceJustNow(){
        List<YogaClassInstance> yogaClassInstanceList = getAllYogaClassInstance();
        return yogaClassInstanceList.get(yogaClassInstanceList.size() - 1);
    }
    public boolean deleteClassInstance(@NonNull YogaClassInstance yogaClassInstance) {
        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete(YogaClassInstance.TABLE_NAME, YogaClassInstance.COLUMN_ID + " = ?", new String[]{String.valueOf(yogaClassInstance.getYoga_class_instance_id())});
        if(delete == 0) return false;
        db.close();
        return true;
    }

    //Many to many User and Yoga Class Instance
    public boolean addUserYogaClassInstance(@NonNull UserYogaClassInstance userYogaClassInstance){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(UserYogaClassInstance.COLUMN_USER_ID, userYogaClassInstance.getUser_id());
        cv.put(UserYogaClassInstance.COLUMN_YOGA_CLASS_INSTANCE_ID, userYogaClassInstance.getYoga_class_instance_id());
        long insert = db.insert(UserYogaClassInstance.TABLE_NAME, null, cv);
        return insert != -1;
    }
    public boolean updateUserYogaClassInstance(@NonNull UserYogaClassInstance userYogaClassInstance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserYogaClassInstance.COLUMN_USER_ID, userYogaClassInstance.getUser_id());
        cv.put(UserYogaClassInstance.COLUMN_YOGA_CLASS_INSTANCE_ID, userYogaClassInstance.getYoga_class_instance_id());
        long update = db.update(UserYogaClassInstance.TABLE_NAME, cv, UserYogaClassInstance.COLUMN_YOGA_CLASS_INSTANCE_ID + " = ?", new String[]{String.valueOf(userYogaClassInstance.getYoga_class_instance_id())});
        return update > 0;
    }
    public List<UserYogaClassInstance> getAllUserYogaClassInstance(){
        List<UserYogaClassInstance> userYogaClassInstanceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = UserYogaClassInstance.GET_ALL_USER_YOGA_CLASS_INSTANCE_LIST;

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()) {
            do {
                int user_id = cursor.getInt(0);
                int yoga_class_instance_id = cursor.getInt(1);
                UserYogaClassInstance userYogaClassInstance = new UserYogaClassInstance(user_id, yoga_class_instance_id);
                userYogaClassInstanceList.add(userYogaClassInstance);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userYogaClassInstanceList;
    }
    public boolean deleteUserYogaClassInstance(@NonNull UserYogaClassInstance userYogaClassInstance) {
        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete(UserYogaClassInstance.TABLE_NAME, UserYogaClassInstance.COLUMN_YOGA_CLASS_INSTANCE_ID + " = ?", new String[]{String.valueOf(userYogaClassInstance.getYoga_class_instance_id())});
        if(delete == 0) return false;
        db.close();
        return true;
    }

    //sync data firebase
    public void addOrUpdateUser(User user) {
        List<User> userList = getAllUser();
        if(userList.isEmpty()) {
            addUser(user);
            return;
        }
        for (User userCheck: userList) {
            if(userCheck.getId() == user.getId()) {
                updateUser(user);
            } else {
                addUser(user);
            }
        }
    }

}