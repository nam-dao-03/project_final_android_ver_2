package com.example.project_final_ver_01.ui.user.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.adapters.non_ui_components.RoleUserAdapter;
import com.example.project_final_ver_01.database.DatabaseHelper;
import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.ui.login.activities.AdminHomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateUsersFragment extends Fragment {
    //Define widgets in Fragment
    private View mView;
    private Spinner spn_role_user;
    private RoleUserAdapter roleUserAdapter;
    private EditText et_email_user, et_name_user, et_phone_number_user, et_description_user;
    private TextView tv_header_user;
    private Button btn_cancel_users, btn_submit_users;
    //Define widgets in dialog
    private TextView tv_confirm_header_user;
    private EditText et_confirm_email_user, et_confirm_role_user, et_confirm_name_user, et_confirm_phone_number_user, et_confirm_description_user;
    private Button btn_dismiss_dialog_confirm_users, btn_submit_dialog_confirm_users;
    //Define activity that fragment is holding
    private AdminHomeActivity mAdminHomeActivity;
    //Define Bundle
    private Bundle bundleReceive;
    //Dropdown
    List<RoleUserAdapter.RoleUser> roleUserList = new ArrayList<>();
    //Define Object
    private User user;
    //Define Database
    private DatabaseHelper databaseHelper;


    public UpdateUsersFragment(){

    }
    @Override
    public void onResume() {
        super.onResume();
        ((AdminHomeActivity) requireActivity()).hideFab();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_add_update_users, container, false);
        initUI();
        setListenersForWidget();
        return mView;
    }

    @SuppressLint("SetTextI18n")
    private void initUI(){
        //Text View
        tv_header_user = mView.findViewById(R.id.tv_header_user);
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

        //Signing variable
        tv_header_user.setText("Update User");
        btn_submit_users.setText("Update");
        bundleReceive = getArguments();
        if(bundleReceive == null) return;
        user = (User) bundleReceive.get("object_user");
        if(user == null) return;
        et_email_user.setText(user.getEmail());
        et_name_user.setText(user.getUser_name());
        et_phone_number_user.setText(user.getPhone_number());
        et_description_user.setText(user.getDescription());
        int position = roleUserAdapter.getPosition(getRoleUser());
        spn_role_user.setSelection(position);
    }
    private void setListenersForWidget() {
        btn_cancel_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdminHomeActivity.replaceFragment(new UsersFragment());
            }
        });
        btn_submit_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String email_user = et_email_user.getText().toString().trim();
                    String role_user = ((RoleUserAdapter.RoleUser) spn_role_user.getSelectedItem()).getName().trim();
                    String phone_number_user = et_phone_number_user.getText().toString().trim();
                    String user_name = et_name_user.getText().toString().trim();
                    String description_user = et_description_user.getText().toString().trim();
                    if(email_user.isEmpty() || role_user.isEmpty() || user_name.isEmpty() || phone_number_user.isEmpty() || description_user.isEmpty()) {
                        createToast("Please fill full input", R.drawable.baseline_warning_24);
                        return;
                    }
                    if(checkUniqueUser(user_name)) {
                        createToast("Already have user name", R.drawable.baseline_warning_24);
                        return;
                    }
                    confirmDialog(email_user,role_user,phone_number_user,user_name,description_user);
                } catch (Exception e) {
                    createToast(e.toString(), R.drawable.baseline_warning_24);
                }
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void confirmDialog(String email_user, String role_user, String phone_number_user, String name_user, String description_user) {
        //Define dialog
        final Dialog dialog = new Dialog(requireActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog_confirm_add_update_user);
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setGravity(Gravity.CENTER);
        //Signing variables
        //Text View
        tv_confirm_header_user = dialog.findViewById(R.id.tv_confirm_header_user);
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
        tv_confirm_header_user.setText("Update User?");
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
                try {
                    user.setEmail(email_user);
                    user.setRole(role_user);
                    user.setPhone_number(phone_number_user);
                    user.setUser_name(name_user);
                    user.setDescription(description_user);
                    boolean result = databaseHelper.updateUser(user);
                    if(!result) {
                        createToast("Error", R.drawable.baseline_warning_24);
                        return;
                    }
                    createToast("Update Success", R.drawable.baseline_check_circle_24);
//                    mAdminHomeActivity.getFirebaseSyncHelper().updateUserToFirebase(user.getId(), user);
                    dialog.dismiss();
                    mAdminHomeActivity.replaceFragment(new UsersFragment());
                } catch (Exception e){
                    createToast("Error", R.drawable.baseline_warning_24);
                }
            }
        });

    }
    private void showDropdownUserRole(){
        roleUserList.add(new RoleUserAdapter.RoleUser("Teacher"));
        roleUserList.add(new RoleUserAdapter.RoleUser("Student"));
        roleUserAdapter = new RoleUserAdapter(requireContext(), R.layout.item_dropdown_selected, roleUserList);
        spn_role_user.setAdapter(roleUserAdapter);
    }
    private RoleUserAdapter.RoleUser getRoleUser(){
        for(RoleUserAdapter.RoleUser roleUser: roleUserList) {
            if(roleUser.getName().equals(user.getRole())){
                return roleUser;
            }
        }
        return null;
    }
    private boolean checkUniqueUser(String user_name) {
        List<User> userList = databaseHelper.getAllUser();
            if(user.getUser_name().equals(user_name)) return false;
        for (User userDb : userList) {
            if ((userDb.getUser_name()).equalsIgnoreCase(user_name)) return true;
        }
        return false;
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
