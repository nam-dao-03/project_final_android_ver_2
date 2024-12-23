package com.example.project_final_ver_01.ui.class_instance.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.adapters.non_ui_components.NameCourseAdapter;
import com.example.project_final_ver_01.adapters.non_ui_components.TeacherAdapter;
import com.example.project_final_ver_01.database.DatabaseHelper;
import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.database.entities.UserYogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaCourse;
import com.example.project_final_ver_01.ui.login.activities.AdminHomeActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddYogaClassInstanceFragment extends Fragment {
    //Define widgets in Fragment
    private View mView;
    private EditText et_day_of_the_week_class, et_description_class;
    private Button btn_schedule_class, btn_cancel_class, btn_create_class;
    private Spinner spn_name_courses, spn_teacher;
    private NameCourseAdapter nameCourseAdapter;
    private TeacherAdapter teacherAdapter;
    //Define widgets in Dialog
    private EditText et_confirm_course_class, et_confirm_day_of_the_week_class, et_confirm_teacher_class, et_confirm_description_class;
    private Button btn_confirm_schedule_class, btn_confirm_back_class, btn_confirm_submit_class;
    //Define Activity
    private AdminHomeActivity mAdminHomeActivity;
    //Define Database;
    private DatabaseHelper databaseHelper;
    //Define Object;
    private YogaClassInstance yogaClassInstance;
    private UserYogaClassInstance userYogaClassInstance;

    public AddYogaClassInstanceFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        mAdminHomeActivity.hideFab();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add_update_class_session, container, false);
        initUI();
        setListenerForWidget();
        return mView;
    }

    private void initUI() {
        //Activity
        mAdminHomeActivity = (AdminHomeActivity) requireActivity();
        //Database
        databaseHelper = mAdminHomeActivity.getDatabaseHelper();
        //Edit texts
        et_day_of_the_week_class = mView.findViewById(R.id.et_day_of_the_week_class);
        et_description_class = mView.findViewById(R.id.et_description_class);
        //Buttons
        btn_schedule_class = mView.findViewById(R.id.btn_schedule_class);
        btn_cancel_class = mView.findViewById(R.id.btn_cancel_class);
        btn_create_class = mView.findViewById(R.id.btn_create_class);
        //Spinners
        spn_name_courses = mView.findViewById(R.id.spn_name_courses);
        spn_teacher = mView.findViewById(R.id.spn_teacher);
        //Dropdown
        showDropdownNameCourses();
        showDropdownTeachers();
    }
    private void setListenerForWidget() {
        //Buttons
        btn_schedule_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),R.style.CustomDatePickerDialog ,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            String name_course = ((NameCourseAdapter.NameCourse) spn_name_courses.getSelectedItem()).getName().trim();
                            YogaCourse yogaCourse = getYogaCourse(name_course);
                            if(yogaCourse == null) return;
                            String day_of_the_week_course = yogaCourse.getDay_of_the_week();
                            String day_of_the_week_class = showDayOfTheWeek(dayOfMonth, month + 1, year);
                            if(!day_of_the_week_class.equals(day_of_the_week_course)) {
                                createToast("Schedule must be " + day_of_the_week_course, R.drawable.baseline_warning_24);
                                return;
                            }
                            @SuppressLint("DefaultLocale") String date = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
                            createToast("Selected Date: " + date, R.drawable.baseline_check_circle_24);
                            btn_schedule_class.setText(date);
                            et_day_of_the_week_class.setText(day_of_the_week_class);
                        } catch (Exception e) {
                            createToast(e.toString(), R.drawable.baseline_warning_24);
                        }
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        btn_cancel_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdminHomeActivity.replaceFragment(new YogaClassInstanceFragment());
            }
        });
        btn_create_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name_course = ((NameCourseAdapter.NameCourse) spn_name_courses.getSelectedItem()).getName().trim();
                    String schedule_class = btn_schedule_class.getText().toString().trim();
                    String day_of_the_week_class = et_day_of_the_week_class.getText().toString().trim();
                    String teacher = ((TeacherAdapter.Teacher) spn_teacher.getSelectedItem()).getName().trim();
                    String description_class = et_description_class.getText().toString().trim();
                    if (name_course.equals("Empty Courses") || schedule_class.isEmpty() || day_of_the_week_class.isEmpty() || teacher.equals("Empty Teachers") || description_class.isEmpty()) {
                        createToast("Please fill full input", R.drawable.baseline_warning_24);
                        return;
                    }
                    confirmDialog(name_course, schedule_class, day_of_the_week_class, teacher, description_class);
                } catch (Exception e) {
                    createToast(e.toString(), R.drawable.baseline_warning_24);
                }
            }
        });
    }
    private void showDropdownNameCourses(){
        List<NameCourseAdapter.NameCourse> nameCourseList = new ArrayList<>();
        List<YogaCourse> yogaCourseList = databaseHelper.getALlYogaCourse();
        if(!yogaCourseList.isEmpty()) {
            for(YogaCourse yogaCourse : yogaCourseList) {
                String courseName = yogaCourse.getCourse_name();
                nameCourseList.add(new NameCourseAdapter.NameCourse(courseName));
            }
        } else {
            nameCourseList.add(new NameCourseAdapter.NameCourse("Empty Courses"));
            spn_name_courses.setEnabled(false);
        }

        nameCourseAdapter = new NameCourseAdapter(requireContext(), R.layout.item_dropdown_selected, nameCourseList);
        spn_name_courses.setAdapter(nameCourseAdapter);
    }
    private void showDropdownTeachers(){
        List<TeacherAdapter.Teacher> teacherList = new ArrayList<>();
        List<User> userList = databaseHelper.getAllUser("Teacher");
        if(!userList.isEmpty()) {
            for(User user : userList) {
                String teacherName = user.getUser_name();
                teacherList.add(new TeacherAdapter.Teacher(teacherName));
            }
        } else {
            teacherList.add(new TeacherAdapter.Teacher("Empty Teachers"));
            spn_teacher.setEnabled(false);
        }
        teacherAdapter = new TeacherAdapter(requireContext(), R.layout.item_dropdown_selected, teacherList);
        spn_teacher.setAdapter(teacherAdapter);
    }
    private void confirmDialog(String name_course, String schedule_class, String day_of_the_week_class, String teacher, String description_class){
        //Define dialog
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog_confirm_add_update_class_instance);
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setGravity(Gravity.CENTER);
        //Signing variable
        et_confirm_course_class = dialog.findViewById(R.id.et_confirm_course_class);
        btn_confirm_schedule_class = dialog.findViewById(R.id.btn_confirm_schedule_class);
        et_confirm_day_of_the_week_class = dialog.findViewById(R.id.et_confirm_day_of_the_week_class);
        et_confirm_teacher_class = dialog.findViewById(R.id.et_confirm_teacher_class);
        et_confirm_description_class = dialog.findViewById(R.id.et_confirm_description_class);
//        //Button action
        btn_confirm_back_class = dialog.findViewById(R.id.btn_confirm_back_class);
        btn_confirm_submit_class = dialog.findViewById(R.id.btn_confirm_submit_class);
//        //Signing values for widget
        et_confirm_course_class.setText(name_course);
        btn_confirm_schedule_class.setText(schedule_class);
        et_confirm_day_of_the_week_class.setText(day_of_the_week_class);
        et_confirm_teacher_class.setText(teacher);
        et_confirm_description_class.setText(description_class);
//        // Add Listener for buttons
        btn_confirm_back_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_confirm_submit_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    yogaClassInstance = new YogaClassInstance(-1, Objects.requireNonNull(getYogaCourse(name_course)).getId(), schedule_class, description_class);
                    boolean resultYogaClassInstance = databaseHelper.addClassInstance(yogaClassInstance);
                    // Created UserYogaClassInstance many to many relationship between User and YogaClassInstance
                    userYogaClassInstance = new UserYogaClassInstance(getUserId("Teacher", teacher) ,databaseHelper.getYogaClassInstanceJustNow().getYoga_class_instance_id());
                    boolean resultUserYogaClassInstance = databaseHelper.addUserYogaClassInstance(userYogaClassInstance);
                    if(!resultYogaClassInstance || !resultUserYogaClassInstance) {
                        createToast("Error", R.drawable.baseline_warning_24);
                        return;
                    }
                    createToast("Add Success", R.drawable.baseline_check_circle_24);
//                    mAdminHomeActivity.getFirebaseSyncHelper().createYogaClassInstanceToFirebase();
//                    mAdminHomeActivity.getFirebaseSyncHelper().createUserYogaClassInstanceToFirebase();
                    dialog.dismiss();
                    mAdminHomeActivity.replaceFragment(new YogaClassInstanceFragment());
                } catch (Exception e) {
                    createToast("Error", R.drawable.baseline_warning_24);
                }
            }
        });
    }
    private String showDayOfTheWeek(int day, int month, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "Unknown";
        }
    }
    private void createToast(String input_text_to_toast, int imageResId){
        Toast toast = new Toast(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_custom_toast, mView.findViewById(R.id.layout_custom_toast));
        TextView text_toast = view.findViewById(R.id.text_toast);
        ImageView img_icon_toast = view.findViewById(R.id.img_icon_toast);
        text_toast.setText(input_text_to_toast);
        img_icon_toast.setImageResource(imageResId);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
    private int getUserId(String role, String user_name) {
        List<User> userList = databaseHelper.getAllUser(role);
        for(User user: userList) {
            if((user.getUser_name()).equalsIgnoreCase(user_name)) {
                return user.getId();
            }
        }
        return -1;
    }
    private YogaCourse getYogaCourse(String name_course){
        List<YogaCourse> yogaCourseList = databaseHelper.getALlYogaCourse();
        for(YogaCourse yogaCourse: yogaCourseList) {
            if ((yogaCourse.getCourse_name()).equalsIgnoreCase(name_course)) {
                return yogaCourse;
            }
        }
        return null;
    }
}