package com.example.project_final_ver_01.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.database.entities.YogaCourse;

public class DetailYogaCourseFragment extends Fragment {
    public static final String TAG = DetailYogaCourseFragment.class.getName();

    private TextView tv_name_course;
    private Button btn_back;

    private View mView;

    public DetailYogaCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_detail_yoga_course, container, false);

        tv_name_course = mView.findViewById(R.id.tv_name_course);
        btn_back = mView.findViewById(R.id.btn_back);

        Bundle bundleReceive = getArguments();
        if(bundleReceive == null) return mView;
        YogaCourse yogaCourse = (YogaCourse) bundleReceive.get("object_yoga_course");
        if(yogaCourse == null) return mView;
        tv_name_course.setText(yogaCourse.getCourse_name());

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return mView;
    }
}