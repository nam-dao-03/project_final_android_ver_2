package com.example.project_final_ver_01.database.entities;

public class YogaCourse {
    //
    public static final String TABLE_NAME = "yoga_course";
    public static final String COLUMN_ID = "yoga_course_id";
    public static final String COLUMN_CAPACITY = "capacity";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_TYPE_OF_CLASS = "type_of_class";
    public static final String COLUMN_PRICE_PER_CLASS = "price_per_class";
    public static final String COLUMN_DESCRIPTION = "description";
    //Variable
    private int yoga_course_id;
    private int capacity;
    private double duration;
    private String type_of_class;
    private double price_per_class;
    private String descriptions;

    public YogaCourse(String descriptions, double price_per_class, String type_of_class, double duration, int capacity, int yoga_course_id) {
        this.descriptions = descriptions;
        this.price_per_class = price_per_class;
        this.type_of_class = type_of_class;
        this.duration = duration;
        this.capacity = capacity;
        this.yoga_course_id = yoga_course_id;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public double getPrice_per_class() {
        return price_per_class;
    }

    public void setPrice_per_class(double price_per_class) {
        this.price_per_class = price_per_class;
    }

    public String getType_of_class() {
        return type_of_class;
    }

    public void setType_of_class(String type_of_class) {
        this.type_of_class = type_of_class;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getId() {
        return yoga_course_id;
    }

    public void setId(int yoga_course_id) {
        this.yoga_course_id = yoga_course_id;
    }

    // SQL Query: Creating the Table
    public static final String CREATE_TABLE =
            "CREATE TABLE " +
                    TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_CAPACITY + " INTEGER," +
                    COLUMN_DURATION + " REAL," +
                    COLUMN_TYPE_OF_CLASS + " TEXT," +
                    COLUMN_PRICE_PER_CLASS + " REAL," +
                    COLUMN_DESCRIPTION + " TEXT" +
                    ")";
}
