package com.example.project_final_ver_01.ui.class_instance.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.database.DatabaseHelper;
import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.database.entities.UserYogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaCourse;
import com.example.project_final_ver_01.ui.login.activities.AdminHomeActivity;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class DetailYogaClassInstanceFragment extends Fragment {
    //Define widgets in fragment
    private View mView;
    private ImageView img_decoration_detail_class;
    private TextView tv_name_detail_course_class, tv_type_of_class_detail_class, tv_day_of_the_week_detail_class, tv_new_day_of_the_week_detail_class, tv_time_detail_of_class, tv_schedule_detail_class, tv_capacity_detail_class, tv_duration_detail_class, tv_description_detail_class, tv_teacher_detail_class;
    private Button btn_delete_detail_class, btn_update_detail_class;
    //Define widgets in dialog
    private TextView tv_delete_name, tv_action_no, tv_action_yes;
    //Activity
    AdminHomeActivity mAdminHomeActivity;
    //Database
    DatabaseHelper databaseHelper;
    //Bundle
    Bundle bundleReceive;
    YogaClassInstance yogaClassInstance;

    public DetailYogaClassInstanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdminHomeActivity.hideFab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_detail_yoga_class_instance, container, false);
        initUI();
        setListenerForWidgets();
        return mView;
    }
    @SuppressLint("SetTextI18n")
    private void initUI() {
        //Activity
        mAdminHomeActivity = (AdminHomeActivity) requireActivity();
        //Database
        databaseHelper = mAdminHomeActivity.getDatabaseHelper();
        //Text View
        tv_name_detail_course_class = mView.findViewById(R.id.tv_name_detail_course_class);
        tv_type_of_class_detail_class = mView.findViewById(R.id.tv_type_of_class_detail_class);
        tv_day_of_the_week_detail_class = mView.findViewById(R.id.tv_day_of_the_week_detail_class);
        tv_new_day_of_the_week_detail_class = mView.findViewById(R.id.tv_new_day_of_the_week_detail_class);
        tv_time_detail_of_class = mView.findViewById(R.id.tv_time_detail_of_class);
        tv_schedule_detail_class = mView.findViewById(R.id.tv_schedule_detail_class);
        tv_teacher_detail_class = mView.findViewById(R.id.tv_teacher_detail_class);
        tv_capacity_detail_class = mView.findViewById(R.id.tv_capacity_detail_class);
        tv_duration_detail_class = mView.findViewById(R.id.tv_duration_detail_class);
        tv_description_detail_class = mView.findViewById(R.id.tv_description_detail_class);
        //Image View
        img_decoration_detail_class = mView.findViewById(R.id.img_decoration_detail_class);
        //Button
        btn_delete_detail_class = mView.findViewById(R.id.btn_delete_detail_class);
        btn_update_detail_class = mView.findViewById(R.id.btn_update_detail_class);
        //Signing variables
        bundleReceive = getArguments();
        if(bundleReceive == null) return;
        yogaClassInstance = (YogaClassInstance) bundleReceive.get("object_yoga_class_instance");
        if(yogaClassInstance == null) return;
        YogaCourse yogaCourse = getYogaCourseById(yogaClassInstance.getYoga_course_id());
        if(yogaCourse == null) return;
        String name_course = yogaCourse.getCourse_name();
        User user = getUserById(Objects.requireNonNull(getUserYogaClassInstanceByYogaClassInstanceId(yogaClassInstance.getYoga_class_instance_id())).getUser_id());
        if(user == null) return;
        tv_name_detail_course_class.setText("Course: " + name_course);
        String type_of_class = yogaCourse.getType_of_class();
        tv_type_of_class_detail_class.setText(type_of_class);
        if(type_of_class.equals("Flow Yoga"))
            img_decoration_detail_class.setImageResource(R.drawable.img_yoga_class_01);
        if(type_of_class.equals("Aerial Yoga"))
            img_decoration_detail_class.setImageResource(R.drawable.img_yoga_class_02);
        if(type_of_class.equals("Family Yoga"))
            img_decoration_detail_class.setImageResource(R.drawable.img_yoga_class_03);
        tv_day_of_the_week_detail_class.setText(showDayOfTheWeek(yogaClassInstance.getSchedule()));
        if(!showDayOfTheWeek(yogaClassInstance.getSchedule()).equals(yogaCourse.getDay_of_the_week())) {
            tv_day_of_the_week_detail_class.setPaintFlags(tv_day_of_the_week_detail_class.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            tv_day_of_the_week_detail_class.setTextColor(Color.RED);
            tv_new_day_of_the_week_detail_class.setText(yogaCourse.getDay_of_the_week());
        } else {
            tv_new_day_of_the_week_detail_class.setText("");
        }
        tv_time_detail_of_class.setText("Time: " + yogaCourse.getTime_of_course());
        tv_schedule_detail_class.setText("Schedule: " + yogaClassInstance.getSchedule());
        tv_teacher_detail_class.setText("Teacher: " + user.getUser_name());
        tv_capacity_detail_class.setText("Capacity: " + yogaCourse.getCapacity() + " persons");
        tv_duration_detail_class.setText("Duration: " + yogaCourse.getDuration() + " minutes");
        tv_description_detail_class.setText(yogaClassInstance.getDescription());
    }
    private void setListenerForWidgets(){
        btn_delete_detail_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(yogaClassInstance.getSchedule());
            }
        });
        btn_update_detail_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdminHomeActivity.transferDataToFragmentPage(new UpdateYogaClassInstanceFragment(),"object_yoga_class_instance", yogaClassInstance);
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void showDeleteDialog(String schedule){
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog_delete);
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
        //Text View
        tv_delete_name = dialog.findViewById(R.id.tv_delete_name);
        tv_action_no = dialog.findViewById(R.id.tv_action_no);
        tv_action_yes = dialog.findViewById(R.id.tv_action_yes);
        //Signing variable
        tv_delete_name.setText("Delete: " + schedule);
        //Set Listener for widgets
        tv_action_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_action_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.dismiss();
                    mAdminHomeActivity.replaceFragment(new YogaClassInstanceFragment());
                    boolean result_1 = databaseHelper.deleteClassInstance(yogaClassInstance);
                    if(!result_1) {
                        createToast("Error", R.drawable.baseline_warning_24);
                        return;
                    }
//                    mAdminHomeActivity.getFirebaseSyncHelper().deleteYogaClassInstanceToFirebase(yogaClassInstance.getYoga_class_instance_id());
                    createToast("Deleted " + yogaClassInstance.getSchedule(), R.drawable.baseline_check_circle_24);
                } catch (Exception e) {
                    Toast.makeText(mAdminHomeActivity, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private User getUserById(int id) {
        List<User> userList = databaseHelper.getAllUser();
        for(User user: userList) {
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    private YogaCourse getYogaCourseById(int id) {
        List<YogaCourse> yogaCourseList = databaseHelper.getALlYogaCourse();
        for(YogaCourse yogaCourse: yogaCourseList) {
            if(yogaCourse.getId() == id) {
                return yogaCourse;
            }
        }
        return null;
    }
    private UserYogaClassInstance getUserYogaClassInstanceByYogaClassInstanceId(int id) {
        List<UserYogaClassInstance> userYogaClassInstanceList = databaseHelper.getAllUserYogaClassInstance();
        for(UserYogaClassInstance userYogaClassInstance: userYogaClassInstanceList) {
            if(userYogaClassInstance.getYoga_class_instance_id() == id) {
                return userYogaClassInstance;
            }
        }
        return null;
    }
    private String showDayOfTheWeek(String dateString){
        String[] parts = dateString.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
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
}