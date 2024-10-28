package com.example.project_final_ver_01.ui.yoga_course.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.adapters.ui_components.YogaCourseViewHolderAdapter;
import com.example.project_final_ver_01.database.DatabaseHelper;
import com.example.project_final_ver_01.database.entities.YogaCourse;
import com.example.project_final_ver_01.interfaces.IClickItemListener;
import com.example.project_final_ver_01.ui.login.activities.AdminHomeActivity;

import java.util.List;


public class YogaCourseFragment extends Fragment {
    //Define recycle view
    private RecyclerView rcv_yoga_course_list;
    //Define Activity
    private AdminHomeActivity mAdminHomeActivity;
    //Define database
    private DatabaseHelper databaseHelper;
    private View mView;
    public YogaCourseFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        mAdminHomeActivity.showFab(); // Hiện FAB khi Fragment này hiện lên
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_yoga_course, container, false);
        initUI();
        return mView;
    }

    private void initUI(){
        //Recycle View
        rcv_yoga_course_list = mView.findViewById(R.id.rcv_yoga_course);
        //Activity
        mAdminHomeActivity = (AdminHomeActivity) requireActivity();
        //Database
        databaseHelper = mAdminHomeActivity.getDatabaseHelper();
        //Render data
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mAdminHomeActivity);
        rcv_yoga_course_list.setLayoutManager(linearLayoutManager);
        YogaCourseViewHolderAdapter yogaCourseAdapter = new YogaCourseViewHolderAdapter(getListYogaCourse(), new IClickItemListener() {
            @Override
            public void onClickItem(Object object) {
                mAdminHomeActivity.transferDataToFragmentPage(new DetailYogaCourseFragment(),"object_yoga_course", object);
            }
        });
        rcv_yoga_course_list.setAdapter(yogaCourseAdapter);
    }

    private List<YogaCourse> getListYogaCourse() {
        return databaseHelper.getALlYogaCourse();
    }
}