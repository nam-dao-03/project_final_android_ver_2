package com.example.project_final_ver_01.database.entities;


import java.io.Serializable;

public class YogaClassInstance implements Serializable {
    //Table name
    public static final String TABLE_NAME = "yoga_class_instance";
    //Columns names
    public static final String COLUMN_ID = "yoga_class_instance_id";
    public static final String COLUMN_YOGA_COURSE_ID = "yoga_course_id";
    public static final String COLUMN_SCHEDULE = "schedule";
    public static final String COLUMN_DESCRIPTION = "description";

    private int yoga_class_instance_id;
    private int yoga_course_id;
    private String schedule;
    private String description;

    public YogaClassInstance(int yoga_class_instance_id,int yoga_course_id, String schedule, String description) {
        this.yoga_class_instance_id = yoga_class_instance_id;
        this.yoga_course_id = yoga_course_id;
        this.schedule = schedule;
        this.description = description;
    }

    public int getYoga_course_id() {
        return yoga_course_id;
    }

    public void setYoga_course_id(int yoga_course_id) {
        this.yoga_course_id = yoga_course_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getYoga_class_instance_id() {
        return yoga_class_instance_id;
    }

    public void setYoga_class_instance_id(int yoga_class_instance_id) {
        this.yoga_class_instance_id = yoga_class_instance_id;
    }

    //SQL Query create table
    public static final String CREATE_TABLE =
            "CREATE TABLE " +
                    TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_YOGA_COURSE_ID + " INTEGER," +
                    COLUMN_SCHEDULE + " TEXT," +
                    COLUMN_DESCRIPTION + " TEXT)";

    public static final String GET_CLASS_LIST =
            "SELECT * FROM " + TABLE_NAME;
}
