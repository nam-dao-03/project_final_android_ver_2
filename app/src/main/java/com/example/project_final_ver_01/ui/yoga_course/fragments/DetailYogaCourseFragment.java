package com.example.project_final_ver_01.ui.yoga_course.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.project_final_ver_01.adapters.ui_components.ListClassInDetailCourseViewHolderAdapter;
import com.example.project_final_ver_01.database.DatabaseHelper;
import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.database.entities.UserYogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaCourse;
import com.example.project_final_ver_01.interfaces.IClickItemListener;
import com.example.project_final_ver_01.ui.class_instance.fragments.DetailYogaClassInstanceFragment;
import com.example.project_final_ver_01.ui.login.activities.AdminHomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailYogaCourseFragment extends Fragment {
    //Define in Fragment
    private TextView tv_name_detail_course, tv_day_of_the_week_detail_course, tv_capacity_detail_course, tv_duration_detail_course, tv_price_per_class_detail_course, tv_type_of_class_detail_course, tv_time_detail_of_course, tv_description_detail_course;
    private ImageView img_decoration_detail_course;
    private View mView;
    private Button btn_delete_detail_course, btn_update_detail_course;
    private RecyclerView rcv_class_instance;
    //Define widgets in dialog
    private TextView tv_delete_name,tv_action_no, tv_action_yes;
    //Receive data
    private Bundle bundleReceive;
    private YogaCourse yogaCourse;

    //Define Activity
    private AdminHomeActivity mAdminHomeActivity;
    //Define Database
    private DatabaseHelper databaseHelper;
    public DetailYogaCourseFragment() {
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
        mView = inflater.inflate(R.layout.fragment_detail_yoga_course, container, false);
        initUI();
        setListenersForWidget();
        return mView;
    }
    @SuppressLint("SetTextI18n")
    private void initUI(){
        //Activity
        mAdminHomeActivity = (AdminHomeActivity) requireActivity();
        //Database
        databaseHelper = mAdminHomeActivity.getDatabaseHelper();
        //Widgets
        tv_name_detail_course = mView.findViewById(R.id.tv_name_detail_course);
        tv_day_of_the_week_detail_course = mView.findViewById(R.id.tv_day_of_the_week_detail_course);
        tv_time_detail_of_course = mView.findViewById(R.id.tv_time_detail_of_course);
        tv_capacity_detail_course = mView.findViewById(R.id.tv_capacity_detail_course);
        tv_duration_detail_course = mView.findViewById(R.id.tv_duration_detail_course);
        tv_type_of_class_detail_course = mView.findViewById(R.id.tv_type_of_class_detail_course);
        img_decoration_detail_course = mView.findViewById(R.id.img_decoration_detail_course);
        tv_price_per_class_detail_course = mView.findViewById(R.id.tv_price_per_class_detail_course);
        tv_description_detail_course = mView.findViewById(R.id.tv_description_detail_course);
        btn_delete_detail_course = mView.findViewById(R.id.btn_delete_detail_course);
        btn_update_detail_course = mView.findViewById(R.id.btn_update_detail_course);
        rcv_class_instance = mView.findViewById(R.id.rcv_class_instance);

        //Signing variable
        bundleReceive = getArguments();
        if(bundleReceive == null) return;
        yogaCourse = (YogaCourse) bundleReceive.get("object_yoga_course");
        if(yogaCourse == null) return;
        tv_name_detail_course.setText("Course: " + yogaCourse.getCourse_name());
        tv_day_of_the_week_detail_course.setText("Start on: " + yogaCourse.getDay_of_the_week());
        tv_time_detail_of_course.setText("Time: " + yogaCourse.getTime_of_course());
        tv_capacity_detail_course.setText("Capacity: " + yogaCourse.getCapacity() + " persons");
        tv_duration_detail_course.setText("Duration: " + yogaCourse.getDuration() + " minutes");
        String type_of_class = yogaCourse.getType_of_class();
        tv_type_of_class_detail_course.setText(type_of_class);
        if(type_of_class.equals("Flow Yoga"))
            img_decoration_detail_course.setImageResource(R.drawable.img_yoga_class_01);
        if(type_of_class.equals("Aerial Yoga"))
            img_decoration_detail_course.setImageResource(R.drawable.img_yoga_class_02);
        if(type_of_class.equals("Family Yoga"))
            img_decoration_detail_course.setImageResource(R.drawable.img_yoga_class_03);
        tv_price_per_class_detail_course.setText("Price: " + yogaCourse.getPrice_per_class() + "$");
        tv_description_detail_course.setText(yogaCourse.getDescriptions());

        //Class Instance List
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setReverseLayout(true);
        rcv_class_instance.setLayoutManager(linearLayoutManager);
        ListClassInDetailCourseViewHolderAdapter listClassInDetailCourseViewHolderAdapter = new ListClassInDetailCourseViewHolderAdapter(getClassInstanceList(), getUserYogaClassInstanceList(), getUserList("Teacher"),getYogaCourseList(),new IClickItemListener() {
            @Override
            public void onClickItem(Object object) {
                mAdminHomeActivity.transferDataToFragmentPage(new DetailYogaClassInstanceFragment(), "object_yoga_class_instance", object);
            }
        });
        rcv_class_instance.setAdapter(listClassInDetailCourseViewHolderAdapter);
    }

    private void setListenersForWidget(){
        btn_delete_detail_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(yogaCourse.getCourse_name());
            }
        });
        btn_update_detail_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdminHomeActivity.transferDataToFragmentPage(new UpdateYogaCourseFragment(),"object_yoga_course", yogaCourse);
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void showDeleteDialog(String name_delete){
        //Define Widgets
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
        tv_delete_name.setText("Delete: " + name_delete);
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
                    if(checkExistYogaCourseInClassInstance(yogaCourse)) {
                        createToast("You must delete class instance first " + yogaCourse.getCourse_name(), R.drawable.baseline_warning_24);
                        dialog.dismiss();
                        return;
                    }
                    boolean result = databaseHelper.deleteCourse(yogaCourse);
                    if (!result) {
                        createToast("Error", R.drawable.baseline_warning_24);
                    }
                        createToast("Deleted " + yogaCourse.getCourse_name(), R.drawable.baseline_check_circle_24);
//                    mAdminHomeActivity.getFirebaseSyncHelper().deleteYogaCourseToFirebase(yogaCourse.getId());
                    dialog.dismiss();
                    mAdminHomeActivity.replaceFragment(new YogaCourseFragment());
                } catch (Exception e) {
                    createToast(e.toString(), R.drawable.baseline_warning_24);
                }
            }
        });
    }
    private boolean checkExistYogaCourseInClassInstance(YogaCourse yogaCourse){
        List<YogaClassInstance> yogaClassInstanceList = databaseHelper.getAllYogaClassInstance();
        for(YogaClassInstance yogaClassInstance: yogaClassInstanceList) {
            if(yogaClassInstance.getYoga_course_id() == yogaCourse.getId()) {
                return true;
            }
        }
        return false;
    }
    private List<YogaClassInstance> getClassInstanceList() {
        List<YogaClassInstance> list = databaseHelper.getAllYogaClassInstance();
        List<YogaClassInstance> yogaClassInstanceList = new ArrayList<>();
        for(YogaClassInstance yogaClassInstance: list) {
            if(yogaClassInstance.getYoga_course_id() == yogaCourse.getId()) {
                yogaClassInstanceList.add(yogaClassInstance);
            }
        }
        return yogaClassInstanceList;
    }
    private List<YogaCourse> getYogaCourseList(){
        return databaseHelper.getALlYogaCourse();
    }
    private List<UserYogaClassInstance> getUserYogaClassInstanceList(){
        return databaseHelper.getAllUserYogaClassInstance();
    }
    private List<User> getUserList(String role){
        return databaseHelper.getAllUser(role);
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