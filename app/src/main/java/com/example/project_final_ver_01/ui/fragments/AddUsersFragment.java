package com.example.project_final_ver_01.ui.fragments;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.adapters.non_ui_components.RoleUserAdapter;
import com.example.project_final_ver_01.database.DatabaseHelper;
import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.ui.activities.AdminHomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddUsersFragment extends Fragment {
    //Define widgets in Fragment
    private Spinner spn_role_user;
    private RoleUserAdapter roleUserAdapter;
    private EditText et_email_user, et_name_user, et_phone_number_user, et_description_user;
    private Button btn_cancel_users, btn_submit_users;
    //Define widgets in dialog
    private EditText et_confirm_email_user, et_confirm_role_user, et_confirm_name_user, et_confirm_phone_number_user, et_confirm_description_user;
    private Button btn_dismiss_dialog_confirm_users, btn_submit_dialog_confirm_users;
    private View mView;
    //Define activity that fragment is holding
    private AdminHomeActivity mAdminHomeActivity;
    //Define Object
    private User user;
    //Define Database
    private DatabaseHelper databaseHelper;


    public AddUsersFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AdminHomeActivity) requireActivity()).hideFab();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add_users, container, false);
        initUI();
        setListenersForWidget();
        return mView;
    }

    private void initUI(){
        //Edit Text
        et_email_user = mView.findViewById(R.id.et_email_user);
        et_name_user = mView.findViewById(R.id.et_name_user);
        et_phone_number_user = mView.findViewById(R.id.et_phone_number_user);
        et_description_user = mView.findViewById(R.id.et_description_user);
        //Spinner to create Dropdown
        spn_role_user = mView.findViewById(R.id.spn_role_user);
        showDropdownUserRole();
        //Button
        btn_cancel_users = mView.findViewById(R.id.btn_cancel_users);
        btn_submit_users = mView.findViewById(R.id.btn_create_users);
        //Activity
        mAdminHomeActivity =(AdminHomeActivity) requireActivity();
        //Database
        databaseHelper = mAdminHomeActivity.getDatabaseHelper();
    }
    private void setListenersForWidget() {
        btn_cancel_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdminHomeActivity.replaceFragment(new YogaCourseFragment());
            }
        });
        btn_submit_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String email_user = et_email_user.getText().toString().trim();
                    String role_user = ((RoleUserAdapter.RoleUser) spn_role_user.getSelectedItem()).getName().trim();
                    String phone_number_user = et_phone_number_user.getText().toString().trim();
                    String name_user = et_name_user.getText().toString().trim();
                    String description_user = et_description_user.getText().toString().trim();
                    if(email_user.isEmpty() || role_user.isEmpty() || name_user.isEmpty() || phone_number_user.isEmpty() || description_user.isEmpty()) {
                        createToast("Please fill full input");
                        return;
                    }
                    confirmDialog(email_user,role_user,phone_number_user,name_user,description_user);
                } catch (Exception e) {
                    createToast(e.toString());
                }
            }
        });
    }
    private void confirmDialog(String email_user, String role_user, String phone_number_user,String name_user, String description_user) {
        //Define dialog
        final Dialog dialog = new Dialog(requireActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog_confirm_add_user);
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setGravity(Gravity.CENTER);
        //Signing variables
        //Edit Text
        et_confirm_email_user = dialog.findViewById(R.id.et_confirm_email_user);
        et_confirm_role_user= dialog.findViewById(R.id.et_confirm_role_user);
        et_confirm_phone_number_user = dialog.findViewById(R.id.et_confirm_phone_number_user);
        et_confirm_name_user = dialog.findViewById(R.id.et_confirm_name_user);
        et_confirm_description_user = dialog.findViewById(R.id.et_confirm_description_user);
        //Button
        btn_dismiss_dialog_confirm_users = dialog.findViewById(R.id.btn_dismiss_dialog_confirm_users);
        btn_submit_dialog_confirm_users = dialog.findViewById(R.id.btn_submit_dialog_confirm_users);
        //Set text for Edit text to confirm for user
        et_confirm_email_user.setText(email_user);
        et_confirm_role_user.setText(role_user);
        et_confirm_phone_number_user.setText(phone_number_user);
        et_confirm_name_user.setText(name_user);
        et_confirm_description_user.setText(description_user);
        //Signing Listener for buttons
        btn_dismiss_dialog_confirm_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_submit_dialog_confirm_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User(-1, email_user, role_user, phone_number_user, name_user, description_user);
                try {
                    boolean result = databaseHelper.addUser(user);
                    if(!result) {
                        Toast.makeText(mAdminHomeActivity, "Error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(mAdminHomeActivity, "Add Success", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    mAdminHomeActivity.replaceFragment(new UsersFragment());
                } catch (Exception e){
                    Toast.makeText(mAdminHomeActivity, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void showDropdownUserRole(){
            List<RoleUserAdapter.RoleUser> list = new ArrayList<>();
            list.add(new RoleUserAdapter.RoleUser("Teacher"));
            list.add(new RoleUserAdapter.RoleUser("Student"));
        roleUserAdapter = new RoleUserAdapter(requireContext(), R.layout.item_dropdown_selected, list);
        spn_role_user.setAdapter(roleUserAdapter);
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