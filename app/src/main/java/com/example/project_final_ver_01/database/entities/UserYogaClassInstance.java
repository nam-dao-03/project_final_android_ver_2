package com.example.project_final_ver_01.database.entities;

import java.io.Serializable;

public class UserYogaClassInstance implements Serializable {
    //
    public static final String TABLE_NAME = "user_yoga_class_instance";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_YOGA_CLASS_INSTANCE_ID = "yoga_class_instance_id";

    //Variables
    private int user_id;
    private int yoga_class_instance_id;

    public UserYogaClassInstance(int user_id, int yoga_class_instance_id) {
        this.user_id = user_id;
        this.yoga_class_instance_id = yoga_class_instance_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getYoga_class_instance_id() {
        return yoga_class_instance_id;
    }

    public void setYoga_class_instance_id(int yoga_class_instance_id) {
        this.yoga_class_instance_id = yoga_class_instance_id;
    }

    // SQL Query: Creating the Table
    public static final String CREATE_TABLE =
            "CREATE TABLE " +
                    TABLE_NAME + "(" +
                    COLUMN_USER_ID + " INTEGER, " +
                    COLUMN_YOGA_CLASS_INSTANCE_ID + " INTEGER, PRIMARY KEY (" +
                    COLUMN_USER_ID + ", " +
                    COLUMN_YOGA_CLASS_INSTANCE_ID + "), FOREIGN KEY (" +
                    COLUMN_USER_ID + ") REFERENCES " +
                    User.TABLE_NAME + "(" +
                    COLUMN_USER_ID + "), FOREIGN KEY (" +
                    COLUMN_YOGA_CLASS_INSTANCE_ID + ") REFERENCES " +
                    YogaClassInstance.TABLE_NAME + "(" +
                    COLUMN_YOGA_CLASS_INSTANCE_ID + "))";

    public static final String GET_ALL_USER_YOGA_CLASS_INSTANCE_LIST =
            "SELECT * FROM " + TABLE_NAME;
}
