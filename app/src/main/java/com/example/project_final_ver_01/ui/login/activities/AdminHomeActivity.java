package com.example.project_final_ver_01.ui.login.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.database.DatabaseHelper;
import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.database.entities.YogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaCourse;
import com.example.project_final_ver_01.ui.class_instance.fragments.AddYogaClassInstanceFragment;
import com.example.project_final_ver_01.ui.class_instance.fragments.DetailYogaClassInstanceFragment;
import com.example.project_final_ver_01.ui.search.activities.SearchActivity;
import com.example.project_final_ver_01.ui.yoga_course.fragments.AddCoursesFragment;
import com.example.project_final_ver_01.ui.user.fragments.AddUsersFragment;
import com.example.project_final_ver_01.ui.class_instance.fragments.YogaClassInstanceFragment;
import com.example.project_final_ver_01.ui.user.fragments.UsersFragment;
import com.example.project_final_ver_01.ui.yoga_course.fragments.DetailYogaCourseFragment;
import com.example.project_final_ver_01.ui.yoga_course.fragments.YogaCourseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;


public class AdminHomeActivity extends AppCompatActivity {

    //In sidebar and floating action button
    private ImageView btn_img_side_bar, btn_img_search;
    private FloatingActionButton fab_add;
    private CardView card_sign_out, card_course_list, card_people, card_class;
    //In dialog bottom when click floating action button
    private TextView btn_add_course,btn_add_users, btn_add_class;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onResume() {
        super.onResume();
        createYogaClassFromSearch();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_admin_home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseHelper = new DatabaseHelper(AdminHomeActivity.this);
        //In sidebar and floating action button
        btn_img_side_bar = findViewById(R.id.btn_img_side_bar);
        btn_img_search = findViewById(R.id.btn_img_search);
        fab_add = findViewById(R.id.fab_add);

        //Create and add first fragment to Activity
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_frame, new YogaCourseFragment());
        fragmentTransaction.commit();


        btn_img_side_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSidebar();
            }
        });

        btn_img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search_activity = new Intent(AdminHomeActivity.this, SearchActivity.class);
                startActivity(search_activity);
                finish();
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogBottom();
            }
        });

//
    }
    public void showFab(){
        fab_add.show();
    }
    public void hideFab(){
        fab_add.hide();
    }
    private void showSidebar(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog_as_sidebar);
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSidebarAnimation;
        dialog.getWindow().setGravity(Gravity.START);

        //Signing for variables
        card_sign_out = dialog.findViewById(R.id.card_sign_out);
        card_course_list = dialog.findViewById(R.id.card_course_list);
        card_people = dialog.findViewById(R.id.card_people);
        card_class = dialog.findViewById(R.id.card_class);
        //Set listener for cards
        card_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_activity = new Intent(AdminHomeActivity.this, LoginActivity.class);
                startActivity(login_activity);
                finish();
            }
        });
        card_course_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new YogaCourseFragment());
                dialog.dismiss();
            }
        });
        card_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new UsersFragment());
                dialog.dismiss();
            }
        });
        card_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new YogaClassInstanceFragment());
                dialog.dismiss();
            }
        });
    }
    private void showDialogBottom() {
        //Define dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog_bottom);
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBottomAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        //Signing for variables
        btn_add_course = dialog.findViewById(R.id.btn_add_course);
        btn_add_users = dialog.findViewById(R.id.btn_add_user);
        btn_add_class = dialog.findViewById(R.id.btn_add_class);
        btn_add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCoursesFragment();
                dialog.dismiss();
            }
        });

        btn_add_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddUsersFragment();
                dialog.dismiss();
            }
        });

        btn_add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddClassSessionFragment();
                dialog.dismiss();
            }
        });
    }
    private void createYogaClassFromSearch(){
        YogaClassInstance yogaClassInstance = (YogaClassInstance) getIntent().getSerializableExtra("object_yoga_class_instance");
        if(yogaClassInstance == null) return;
        transferDataToFragmentPage(new DetailYogaClassInstanceFragment(), "object_yoga_class_instance", yogaClassInstance);
    }
    public void transferDataToFragmentPage(Fragment fragment, String key, Object object) {
        Bundle bundle = new Bundle();
        if(object instanceof YogaCourse) {
            bundle.putSerializable(key, (YogaCourse) object);
        }
        if(object instanceof User) {
            bundle.putSerializable(key, (User) object);
        }
        if(object instanceof YogaClassInstance) {
            bundle.putSerializable(key, (YogaClassInstance) object);
        }
        fragment.setArguments(bundle);
        replaceFragment(fragment);
            }
    private void showAddUsersFragment(){
        replaceFragment(new AddUsersFragment());
    }
    private void showAddCoursesFragment(){
        replaceFragment(new AddCoursesFragment());
    }
    private void showAddClassSessionFragment(){
        replaceFragment(new AddYogaClassInstanceFragment());
    }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    //Working with database
    public DatabaseHelper getDatabaseHelper(){
        return databaseHelper;
    }
}