package com.example.project_final_ver_01.database.entities;

public class User {
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "user_id";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_DESCRIPTION = "description";

    //Variables
    private int user_id;
    private String phone_number;
    private String role;
    private String email;
    private String description;

    public User(int user_id, String phone_number, String email, String description, String role) {
        this.user_id = user_id;
        this.phone_number = phone_number;
        this.role = role;
        this.email = email;
        this.description = description;
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

    public String getName() {
        return email;
    }

    public void setName(String email) {
        this.email = email;
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
                    COLUMN_PHONE_NUMBER + " TEXT," +
                    COLUMN_DESCRIPTION + " TEXT" +
                    ")";
}
