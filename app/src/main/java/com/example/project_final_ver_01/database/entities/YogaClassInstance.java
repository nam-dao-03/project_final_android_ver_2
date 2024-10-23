package com.example.project_final_ver_01.database.entities;


public class YogaClassInstance {
    //Table name
    public static final String TABLE_NAME = "yoga_class_instance";
    //Columns names
    public static final String COLUMN_ID = "yoga_class_instance_id";
    public static final String COLUMN_CLASS_NAME = "class_name";
    public static final String COLUMN_SCHEDULE = "schedule";
    public static final String COLUMN_DESCRIPTION = "description";

    private int yoga_class_instance_id;
    private String class_name;
    private String schedule;
    private String description;

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
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
                    COLUMN_CLASS_NAME + " TEXT," +
                    COLUMN_SCHEDULE + " TEXT," +
                    COLUMN_DESCRIPTION + " TEXT)";

}
