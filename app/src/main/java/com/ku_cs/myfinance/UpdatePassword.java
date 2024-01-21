package com.ku_cs.myfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatePassword extends AppCompatActivity {

    Button btn_update;
    TextView hint;
    EditText old_password, password1, password2;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        setTitle(R.string.update_password);

        password1 = findViewById(R.id.et_up_password1);
        password2 = findViewById(R.id.et_up_password2);
        hint = findViewById(R.id.tv_up_password_hint);
        btn_update = findViewById(R.id.btn_up_update);
        old_password = findViewById(R.id.et_up_old_password);

        SharedPreferences pref = getSharedPreferences("com.ku_cs.myfinance_preferences", MODE_PRIVATE);
        password = pref.getString("Password", "");
        Intent intent;
        if (password.length() < 1) {
            intent = new Intent(UpdatePassword.this, SetPassword.class);
            startActivity(intent);
            finish();
        }

        btn_update.setOnClickListener(v -> {
            if (old_password.getText().toString().equals(password)
                    && password1.getText().toString().equals(password2.getText().toString())
                    && password1.getText().length() > 1) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Password", password1.getText().toString());
                if (editor.commit()) {
                    Toast.makeText(UpdatePassword.this, R.string.password_saved, Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    hint.setText(R.string.password_didnt_updated);
                }
            } else if (!old_password.getText().toString().equals(password)) {
                hint.setText(R.string.incorrect_password);
            } else if (!password1.getText().toString().equals(password2.getText().toString())) {
                hint.setText(R.string.password_isnt_the_same);
            }else {
                hint.setText(R.string.password_length);
            }
        });
    }
}