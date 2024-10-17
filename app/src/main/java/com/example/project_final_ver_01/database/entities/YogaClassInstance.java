package com.example.project_final_ver_01.database.entities;


import java.time.LocalDate;

public class YogaClassInstance {

    public static final String TABLE_NAME = "yoga_class_instance";
    public static final String COLUMN_ID = "yoga_class_instance_id";
    public static final String COLUMN_NAME = "class_name";
    public static final String COLUMN_DATE = "class_date";

    private int yoga_class_instance_id;
    private String class_name;
    private LocalDate class_date;

    public YogaClassInstance(LocalDate class_date, String class_name, int yoga_class_instance_id) {
        this.class_date = class_date;
        this.class_name = class_name;
        this.yoga_class_instance_id = yoga_class_instance_id;
    }

    public int getYoga_class_instance_id() {
        return yoga_class_instance_id;
    }

    public void setYoga_class_instance_id(int yoga_class_instance_id) {
        this.yoga_class_instance_id = yoga_class_instance_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public LocalDate getClass_date() {
        return class_date;
    }

    public void setClass_date(LocalDate class_date) {
        this.class_date = class_date;
    }

    //SQL Query create table
    public static final String CREATE_TABLE =
            "CREATE TABLE " +
                    TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_DATE + " TEXT)";

}
