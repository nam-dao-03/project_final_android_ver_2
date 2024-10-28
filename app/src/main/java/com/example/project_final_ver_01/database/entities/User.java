package com.example.project_final_ver_01.database.entities;

import java.io.Serializable;

public class User implements Serializable {
    //Table name
    public static final String TABLE_NAME = "user";
    //Columns name
    public static final String COLUMN_ID = "user_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_NAME = "user_name";
    public static final String COLUMN_DESCRIPTION = "description";

    //Variables
    private int user_id;
    private String email;
    private String role;
    private String phone_number;
    private String user_name;
    private String description;
    public User(int user_id, String email, String role, String phone_number, String user_name, String description) {
        this.user_id = user_id;
        this.email = email;
        this.role = role;
        this.phone_number = phone_number;
        this.user_name = user_name;
        this.description = description;
    }
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return user_id;
    }

    public void setId(int user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    // SQL Query: Creating the Table
    public static final String CREATE_TABLE =
            "CREATE TABLE " +
                    TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_ROLE + " TEXT," +
                    COLUMN_PHONE_NUMBER + " TEXT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_DESCRIPTION + " TEXT" +
                    ")";
    //Filter data
    public static final String GET_USER_LIST =
            "SELECT * FROM " + TABLE_NAME;
}
