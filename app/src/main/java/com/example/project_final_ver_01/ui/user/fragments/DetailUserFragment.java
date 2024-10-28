package com.example.project_final_ver_01.ui.user.fragments;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.database.DatabaseHelper;
import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.database.entities.UserYogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaClassInstance;
import com.example.project_final_ver_01.ui.login.activities.AdminHomeActivity;

import java.util.List;
import java.util.Objects;


public class DetailUserFragment extends Fragment {
    //Define widgets in fragment
    private View mView;
    private TextView tv_name_detail_user, tv_role_detail_user, tv_email_detail_user, tv_phone_number_detail_user, tv_description_detail_user;
    private Button btn_update_detail_user, btn_delete_detail_user;
    //Define widgets in dialog
    private TextView tv_delete_name, tv_action_no, tv_action_yes;
    //Activity
    private AdminHomeActivity mAdminHomeActivity;
    //Database
    private DatabaseHelper databaseHelper;
    //Bundle
    private Bundle bundleReceive;
    //Object
    private User user;
    private YogaClassInstance yogaClassInstance;
    private UserYogaClassInstance userYogaClassInstance;
    public DetailUserFragment() {
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
        mView =  inflater.inflate(R.layout.fragment_detail_user, container, false);
        initUI();
        setListenerForWidget();
        return mView;
    }
    private void initUI(){
        //Activity
        mAdminHomeActivity = (AdminHomeActivity) requireActivity();
        //Database
        databaseHelper = mAdminHomeActivity.getDatabaseHelper();
        //Widgets
        tv_name_detail_user = mView.findViewById(R.id.tv_name_detail_user);
        tv_role_detail_user = mView.findViewById(R.id.tv_role_detail_user);
        tv_email_detail_user = mView.findViewById(R.id.tv_email_detail_user);
        tv_phone_number_detail_user = mView.findViewById(R.id.tv_phone_number_detail_user);
        tv_description_detail_user = mView.findViewById(R.id.tv_description_detail_user);
        btn_update_detail_user = mView.findViewById(R.id.btn_update_detail_user);
        btn_delete_detail_user = mView.findViewById(R.id.btn_delete_detail_user);

        //Signing variable
        bundleReceive = getArguments();
        if(bundleReceive == null) return;
        user = (User) bundleReceive.get("object_user");
        if(user == null) return;
        tv_name_detail_user.setText(user.getUser_name());
        tv_role_detail_user.setText(user.getRole());
        tv_email_detail_user.setText(user.getEmail());
        tv_phone_number_detail_user.setText(user.getPhone_number());
        tv_description_detail_user.setText(user.getDescription());
    }
    private void setListenerForWidget(){
        btn_update_detail_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdminHomeActivity.transferDataToFragmentPage(new UpdateUsersFragment(), "object_user", user);
            }
        });
        btn_delete_detail_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(user.getUser_name());
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void showDeleteDialog(String user_name){
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

        //Signing variables
        tv_delete_name.setText("Delete: " + user_name);
        //Set listener for widgets
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
                    yogaClassInstance = getYogaClassInstanceById(Objects.requireNonNull(getUserYogaClassInstanceByUserId(user.getId())).getYoga_class_instance_id());
                    if (yogaClassInstance != null) {
                        createToast("You must delete class instance first " + yogaClassInstance.getSchedule(), R.drawable.baseline_warning_24);
                        dialog.dismiss();
                        return;
                    }
                    dialog.dismiss();
                    mAdminHomeActivity.replaceFragment(new UsersFragment());
                    boolean result = databaseHelper.deleteUser(user);
                    if(!result) {
                        createToast("Error", R.drawable.baseline_warning_24);
                        return;
                    }
                    createToast("Deleted " + user.getUser_name(), R.drawable.baseline_check_circle_24);
                } catch (Exception e) {
                    Toast.makeText(mAdminHomeActivity, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private YogaClassInstance getYogaClassInstanceById(int id) {
        List<YogaClassInstance> yogaClassInstanceList = databaseHelper.getAllYogaClassInstance();
        for(YogaClassInstance yogaClassInstance: yogaClassInstanceList) {
            if(yogaClassInstance.getYoga_class_instance_id() == id) {
                return yogaClassInstance;
            }
        }
        return null;
    }
    private UserYogaClassInstance getUserYogaClassInstanceByUserId(int id) {
        List<UserYogaClassInstance> userYogaClassInstanceList = databaseHelper.getAllUserYogaClassInstance();
        for(UserYogaClassInstance userYogaClassInstance: userYogaClassInstanceList) {
            if(userYogaClassInstance.getUser_id() == id) {
                return userYogaClassInstance;
            }
        }
        return null;
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