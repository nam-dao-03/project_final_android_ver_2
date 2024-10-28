package com.example.project_final_ver_01.ui.class_instance.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.adapters.ui_components.YogaClassInstanceViewHolderAdapter;
import com.example.project_final_ver_01.database.DatabaseHelper;
import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.database.entities.UserYogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaCourse;
import com.example.project_final_ver_01.interfaces.IClickItemListener;
import com.example.project_final_ver_01.ui.login.activities.AdminHomeActivity;

import java.util.List;

public class YogaClassInstanceFragment extends Fragment {
    //Define widget in Fragment
    private View mView;
    private RecyclerView rcv_class_instance;
    // Activity
    private AdminHomeActivity mAdminHomeActivity;
    //Database
    private DatabaseHelper databaseHelper;
    public YogaClassInstanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdminHomeActivity.showFab();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_class_instance, container, false);
        initUI();
        showClassInstanceList(mAdminHomeActivity);
        return mView;
    }
    private void initUI(){
        //Signing Activity
        mAdminHomeActivity = (AdminHomeActivity) requireActivity();
        //Signing Database
        databaseHelper = mAdminHomeActivity.getDatabaseHelper();
        //Signing widgets
    }
    private void showClassInstanceList(Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setReverseLayout(true);
        rcv_class_instance = mView.findViewById(R.id.rcv_class_instance);
        rcv_class_instance.setLayoutManager(linearLayoutManager);
        YogaClassInstanceViewHolderAdapter yogaClassInstanceViewHolderAdapter = new YogaClassInstanceViewHolderAdapter(getClassInstanceList(), getYogaCourseList(), getUserList("Teacher"), getUserYogaClassInstanceList(), new IClickItemListener() {
            @Override
            public void onClickItem(Object object) {
                mAdminHomeActivity.transferDataToFragmentPage(new DetailYogaClassInstanceFragment(), "object_yoga_class_instance",object);
            }
        });
        rcv_class_instance.setAdapter(yogaClassInstanceViewHolderAdapter);
    }

    private List<YogaClassInstance> getClassInstanceList() {
        return databaseHelper.getAllYogaClassInstance();
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
}