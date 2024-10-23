package com.example.project_final_ver_01.ui.fragments;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.adapters.non_ui_components.NameCourseAdapter;
import com.example.project_final_ver_01.adapters.non_ui_components.TeacherAdapter;
import com.example.project_final_ver_01.database.DatabaseHelper;
import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.database.entities.YogaCourse;
import com.example.project_final_ver_01.ui.activities.AdminHomeActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddClassSessionFragment extends Fragment {
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

    public AddClassSessionFragment() {
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
        mView = inflater.inflate(R.layout.fragment_add_class_session, container, false);
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
                        String date = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
                        Toast.makeText(requireContext(),"Selected Date: " + date, Toast.LENGTH_SHORT).show();
                        String day_of_the_week = showDayOfTheWeek(dayOfMonth, month + 1, year);
                        btn_schedule_class.setText(date);
                        et_day_of_the_week_class.setText(day_of_the_week);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        btn_cancel_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdminHomeActivity.replaceFragment(new ClassInstanceFragment());
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
                    if (name_course.isEmpty() || schedule_class.isEmpty() || day_of_the_week_class.isEmpty() || teacher.isEmpty() || description_class.isEmpty()) {
                        createToast("Please fill full input");
                        return;
                    }
                    confirmDialog(name_course, schedule_class, day_of_the_week_class, teacher, description_class);
                } catch (Exception e) {
                    createToast(e.toString());
                }
            }
        });
    }
    private void showDropdownNameCourses(){
        List<NameCourseAdapter.NameCourse> nameCourseList = new ArrayList<>();
        List<YogaCourse> yogaCourseList = databaseHelper.getALlYogaCourse();
        for(YogaCourse yogaCourse : yogaCourseList) {
            String courseName = yogaCourse.getCourse_name();
            nameCourseList.add(new NameCourseAdapter.NameCourse(courseName));
        }
        nameCourseAdapter = new NameCourseAdapter(requireContext(), R.layout.item_dropdown_selected, nameCourseList);
        spn_name_courses.setAdapter(nameCourseAdapter);
    }
    private void showDropdownTeachers(){
        List<TeacherAdapter.Teacher> teacherList = new ArrayList<>();
        List<User> userList = databaseHelper.getAllUser("Teacher");
        for(User user : userList) {
            String teacherName = user.getUser_name();
            teacherList.add(new TeacherAdapter.Teacher(teacherName));
        }
        teacherAdapter = new TeacherAdapter(requireContext(), R.layout.item_dropdown_selected, teacherList);
        spn_teacher.setAdapter(teacherAdapter);
    }
    private void confirmDialog(String name_course, String schedule_class, String day_of_the_week_class, String teacher, String description_class){
        //Define dialog
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog_confirm_add_class_instance);
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
    private void createToast(String input_text_to_toast){
        Toast toast = new Toast(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_custom_toast, mView.findViewById(R.id.layout_custom_toast));
        TextView text_toast = view.findViewById(R.id.text_toast);
        text_toast.setText(input_text_to_toast);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}