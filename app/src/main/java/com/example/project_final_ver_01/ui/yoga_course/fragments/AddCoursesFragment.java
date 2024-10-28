package com.example.project_final_ver_01.ui.yoga_course.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.adapters.non_ui_components.DayOfTheWeekAdapter;
import com.example.project_final_ver_01.adapters.non_ui_components.TypeOfClassCourseAdapter;
import com.example.project_final_ver_01.database.DatabaseHelper;
import com.example.project_final_ver_01.database.entities.YogaCourse;
import com.example.project_final_ver_01.ui.login.activities.AdminHomeActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class AddCoursesFragment extends Fragment {
    //Define widgets in Fragment
    private View mView;
    private EditText et_name_course, et_duration_course, et_price_per_class_course,et_description_course, et_capacity_course;
    private Button  btn_time_of_course;
    private Button btn_cancel_course, btn_submit_course;
    private Spinner spn_type_of_class_course, spn_day_of_the_week_course;
    private TypeOfClassCourseAdapter typeOfClassCourseAdapter;
    private DayOfTheWeekAdapter dayOfTheWeekAdapter;
    //Define widgets in dialog
    private EditText et_confirm_name_course, et_confirm_day_of_the_week_course,et_confirm_capacity_course, et_confirm_duration_course, et_confirm_type_of_class_course, et_confirm_price_per_class_course, et_confirm_description_course;
    private Button btn_confirm_time_of_course;
    private Button btn_dismiss_dialog_confirm_course, btn_submit_dialog_confirm_course;
    //Define activity that fragment is holding
    private AdminHomeActivity mAdminHomeActivity;
    //Define Object
    private YogaCourse yogaCourse;
    private List<TypeOfClassCourseAdapter.TypeOfClassCourse> typeOfClassCoursesList = new ArrayList<>();
    private List<DayOfTheWeekAdapter.DayOfTheWeek> dayOfTheWeeksList = new ArrayList<>();
    //Define Database
    private DatabaseHelper databaseHelper;
    public AddCoursesFragment() {
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
        mView = inflater.inflate(R.layout.fragment_add_update_course, container, false);
        initUI();
        setListenersForWidget();
        return mView;
    }

    private void initUI(){
        //Edit Text
        et_name_course = mView.findViewById(R.id.et_name_course);
        et_duration_course = mView.findViewById(R.id.et_duration_course);
        et_price_per_class_course = mView.findViewById(R.id.et_price_per_class_course);
        et_description_course = mView.findViewById(R.id.et_description_course);
        et_capacity_course = mView.findViewById(R.id.et_capacity_course);
        //Buttons
        btn_time_of_course = mView.findViewById(R.id.btn_time_of_course);
        btn_cancel_course = mView.findViewById(R.id.btn_cancel_course);
        btn_submit_course = mView.findViewById(R.id.btn_create_course);
        //Spinner
        spn_type_of_class_course = mView.findViewById(R.id.spn_type_of_class_course);
        spn_day_of_the_week_course = mView.findViewById(R.id.spn_day_of_the_week_course);
        //Dropdown
        showDropdownTypeOfClassCourse();
        showDropdownDayOfTheWeek();
        //Activity
        mAdminHomeActivity = (AdminHomeActivity) requireActivity();
        //Database
        databaseHelper = mAdminHomeActivity.getDatabaseHelper();
    }
    private void setListenersForWidget(){
        btn_time_of_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int min = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), R.style.CustomDatePickerDialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        @SuppressLint("DefaultLocale") String time = String.format( "%02d:%02d", hourOfDay, minute);
                        Toast.makeText(requireContext(), "Selected Date: " +time, Toast.LENGTH_SHORT).show();
                        btn_time_of_course.setText(time);
                    }
                }, hour, min, true);
                timePickerDialog.show();

            }
        });

        btn_cancel_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdminHomeActivity.replaceFragment(new YogaCourseFragment());
            }
        });
        btn_submit_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name_course = et_name_course.getText().toString().trim();
                    String day_of_the_week_course =((DayOfTheWeekAdapter.DayOfTheWeek) spn_day_of_the_week_course.getSelectedItem()).getName().trim();
                    String time_of_course = btn_time_of_course.getText().toString().trim();
                    String capacity_course = et_capacity_course.getText().toString().trim();
                    String duration_course = et_duration_course.getText().toString().trim();
                    String type_of_class_course = ((TypeOfClassCourseAdapter.TypeOfClassCourse) spn_type_of_class_course.getSelectedItem()).getName().trim();
                    String price_per_class = et_price_per_class_course.getText().toString().trim();
                    String description_course = et_description_course.getText().toString().trim();
                    if(name_course.isEmpty()  || day_of_the_week_course.isEmpty() || time_of_course.isEmpty() || duration_course.isEmpty() || type_of_class_course.isEmpty() || price_per_class.isEmpty() || description_course.isEmpty()) {
                        createToast("Please fill full input", R.drawable.baseline_warning_24);
                        return;
                    }
                    confirmDialog(name_course, day_of_the_week_course, time_of_course, capacity_course, duration_course, type_of_class_course, price_per_class, description_course);
                } catch (Exception e) {
                    createToast(e.toString(), R.drawable.baseline_warning_24);
                }
            }
        });
    }
    private void confirmDialog(String name_course, String day_of_the_week_course, String time_of_course, String capacity_course, String duration_course, String type_of_class_course, String price_per_class_course, String description_course){
        //Define Dialog
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog_confirm_add_update_course);
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setGravity(Gravity.CENTER);
        //Signing variable
        //Edit Text
        et_confirm_name_course = dialog.findViewById(R.id.et_confirm_name_course);
        et_confirm_day_of_the_week_course = dialog.findViewById(R.id.et_confirm_day_of_the_week_course);
        et_confirm_capacity_course = dialog.findViewById(R.id.et_confirm_capacity_course);
        et_confirm_duration_course = dialog.findViewById(R.id.et_confirm_duration_course);
        et_confirm_type_of_class_course = dialog.findViewById(R.id.et_confirm_type_of_class_course);
        et_confirm_price_per_class_course = dialog.findViewById(R.id.et_confirm_price_per_class_course);
        et_confirm_description_course = dialog.findViewById(R.id.et_confirm_description_course);
        //Buttons
        //Buttons disable
        btn_confirm_time_of_course = dialog.findViewById(R.id.btn_confirm_time_of_course);
        //Buttons enable
        btn_dismiss_dialog_confirm_course = dialog.findViewById(R.id.btn_dismiss_dialog_confirm_course);
        btn_submit_dialog_confirm_course = dialog.findViewById(R.id.btn_submit_dialog_confirm_course);
        //Signing values for widget
        et_confirm_name_course.setText(name_course);
        et_confirm_day_of_the_week_course.setText(day_of_the_week_course);
        btn_confirm_time_of_course.setText(time_of_course);
        et_confirm_capacity_course.setText(capacity_course);
        et_confirm_duration_course.setText(duration_course);
        et_confirm_type_of_class_course.setText(type_of_class_course);
        et_confirm_price_per_class_course.setText(price_per_class_course);
        et_confirm_description_course.setText(description_course);
        // Signing Listener for buttons
        btn_dismiss_dialog_confirm_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_submit_dialog_confirm_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yogaCourse = new YogaCourse(-1, name_course, day_of_the_week_course, time_of_course,Integer.parseInt(capacity_course), Integer.parseInt(duration_course), type_of_class_course, Double.parseDouble(price_per_class_course), description_course);
                try {
                    boolean result = databaseHelper.addCourse(yogaCourse);
                    dialog.dismiss();
                    if(!result) {
                        createToast("Error", R.drawable.baseline_warning_24);
                        return;
                    }
                    createToast("Add Success", R.drawable.baseline_check_circle_24);
                    mAdminHomeActivity.replaceFragment(new YogaCourseFragment());
                } catch (Exception e) {
                    createToast("Error", R.drawable.baseline_warning_24);
                }
            }
        });
    }
    private void showDropdownTypeOfClassCourse(){
        typeOfClassCoursesList.add(new TypeOfClassCourseAdapter.TypeOfClassCourse("Flow Yoga"));
        typeOfClassCoursesList.add(new TypeOfClassCourseAdapter.TypeOfClassCourse("Aerial Yoga"));
        typeOfClassCoursesList.add(new TypeOfClassCourseAdapter.TypeOfClassCourse("Family Yoga"));
        typeOfClassCourseAdapter = new TypeOfClassCourseAdapter(requireContext(), R.layout.item_dropdown_selected, typeOfClassCoursesList);
        spn_type_of_class_course.setAdapter(typeOfClassCourseAdapter);
    }
    private void showDropdownDayOfTheWeek(){

        dayOfTheWeeksList.add(new DayOfTheWeekAdapter.DayOfTheWeek("Monday"));
        dayOfTheWeeksList.add(new DayOfTheWeekAdapter.DayOfTheWeek("Tuesday"));
        dayOfTheWeeksList.add(new DayOfTheWeekAdapter.DayOfTheWeek("Wednesday"));
        dayOfTheWeeksList.add(new DayOfTheWeekAdapter.DayOfTheWeek("Thursday"));
        dayOfTheWeeksList.add(new DayOfTheWeekAdapter.DayOfTheWeek("Friday"));
        dayOfTheWeeksList.add(new DayOfTheWeekAdapter.DayOfTheWeek("Saturday"));
        dayOfTheWeeksList.add(new DayOfTheWeekAdapter.DayOfTheWeek("Sunday"));
        dayOfTheWeekAdapter = new DayOfTheWeekAdapter(requireContext(), R.layout.item_dropdown_selected, dayOfTheWeeksList);
        spn_day_of_the_week_course.setAdapter(dayOfTheWeekAdapter);
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
}