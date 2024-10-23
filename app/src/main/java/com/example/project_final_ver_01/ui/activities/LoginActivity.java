package com.example.project_final_ver_01.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    EditText et_email_login, et_password_login;
    Button btn_login;
    private final String email = "admin@gmail.com";
    private final String password = "Passexam123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseHelper db = new DatabaseHelper(this);
        // declaring widget
        et_email_login = this.findViewById(R.id.et_email_login);
        et_password_login = this.findViewById(R.id.et_password_login);
        btn_login = this.findViewById(R.id.btn_login);
        //set up interactive widget
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // production
//                    String email_input =  et_email_login.getText().toString().toLowerCase().trim();
//                    String password_input = et_password_login.getText().toString().trim();
//                    if(email_input.equals(email) && password_input.equals(password)) {
//                        et_email_login.setText("");
//                        et_password_login.setText("");
//                        Intent activity_admin_home = new Intent(LoginActivity.this, AdminHomeActivity.class);
//                        activity_admin_home.putExtra("email", email_input);
//                        activity_admin_home.putExtra("password", password_input);
//                        startActivity(activity_admin_home);
//                    };
                    Intent activity_admin_home = new Intent(LoginActivity.this, AdminHomeActivity.class);
                    startActivity(activity_admin_home);
                    createToast("Invalid Email or Password");
                } catch (Exception e) {
                    createToast("Missing Email or Password");
                }
            }
        });
    }
    private void createToast(String input_text_to_toast){
        Toast toast = new Toast(LoginActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_custom_toast, this.findViewById(R.id.layout_custom_toast));
        TextView text_toast = view.findViewById(R.id.text_toast);
        text_toast.setText(input_text_to_toast);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
