package com.example.project_final_ver_01.ui.user.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.adapters.ui_components.UserViewHolderAdapter;
import com.example.project_final_ver_01.database.DatabaseHelper;
import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.interfaces.IClickItemListener;
import com.example.project_final_ver_01.ui.login.activities.AdminHomeActivity;

import java.util.List;


public class UsersFragment extends Fragment {
    private View mView;
    private AdminHomeActivity mAdminHomeActivity;
    private RecyclerView rcv_teacher_list, rcv_student_list;
    //Define Database
    private DatabaseHelper databaseHelper;

    public UsersFragment() {
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
        mView = inflater.inflate(R.layout.fragment_users, container, false);
        initUI();
        showUserList(mAdminHomeActivity);
        return mView;
    }

    private void initUI() {
        mAdminHomeActivity = (AdminHomeActivity) requireActivity();
        databaseHelper = mAdminHomeActivity.getDatabaseHelper();
    }
    private void showUserList(Context context){
        LinearLayoutManager linearLayoutManagerTeacher = new LinearLayoutManager(context);
        LinearLayoutManager linearLayoutManagerStudent = new LinearLayoutManager(context);
        linearLayoutManagerTeacher.setReverseLayout(true);
        linearLayoutManagerStudent.setReverseLayout(true);
        rcv_teacher_list = mView.findViewById(R.id.rcv_teacher_list);
        rcv_student_list = mView.findViewById(R.id.rcv_student_list);
        rcv_teacher_list.setLayoutManager(linearLayoutManagerTeacher);
        rcv_student_list.setLayoutManager(linearLayoutManagerStudent);

        UserViewHolderAdapter userAdapterTeacher = new UserViewHolderAdapter(getListUser("Teacher"), new IClickItemListener() {
            @Override
            public void onClickItem(Object object) {
                mAdminHomeActivity.transferDataToFragmentPage(new DetailUserFragment(),"object_user", object);
            }
        });
        rcv_teacher_list.setAdapter(userAdapterTeacher);
        UserViewHolderAdapter userAdapterStudent = new UserViewHolderAdapter(getListUser("Student"), new IClickItemListener() {
            @Override
            public void onClickItem(Object object) {
                mAdminHomeActivity.transferDataToFragmentPage(new DetailUserFragment(), "object_user", object);
            }
        });
        rcv_student_list.setAdapter(userAdapterStudent);
    }
    private List<User> getListUser(String role){
        return databaseHelper.getAllUser(role);
    }
}