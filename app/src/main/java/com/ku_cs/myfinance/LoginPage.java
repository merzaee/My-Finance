package com.ku_cs.myfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    EditText et_password;
    Button login;
    TextView hint;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        et_password = findViewById(R.id.et_lp_password1);
        login = findViewById(R.id.btn_lp_login);
        hint = findViewById(R.id.tv_lp_password_hint);
        SharedPreferences pref = getSharedPreferences("com.ku_cs.myfinance_preferences", MODE_PRIVATE);
        password = pref.getString("Password", "");
        Intent intent;
        if (password.length() < 1) {
            intent = new Intent(LoginPage.this, Contacts.class);
            startActivity(intent);
            finish();
        }
        login.setOnClickListener(v -> {
            if (password.equals(et_password.getText().toString())) {
                Intent intent2 = new Intent(LoginPage.this, Contacts.class);
                startActivity(intent2);
                finish();
            } else {
                hint.setText(R.string.incorrect_password);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    et_password.setFocusedByDefault(true);
                }
               hint.setVisibility(View.VISIBLE);

            }
        });
    }
}