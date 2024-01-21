package com.ku_cs.myfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetPassword extends AppCompatActivity implements View.OnClickListener {

    TextView hint;
    EditText password1, password2;
    Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        setTitle(R.string.set_password);
        initiateViews();
    }

    private void initiateViews() {
        password1 = findViewById(R.id.et_sp_password1);
        password2 = findViewById(R.id.et_sp_password2);
        hint = findViewById(R.id.tv_sp_password_hint);
        btn_save = findViewById(R.id.btn_sp_save);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_sp_save){
            if (password2.getText().toString().equals(password1.getText().toString()) && password1.getText().length() > 0){

                SharedPreferences pref = getSharedPreferences("com.ku_cs.myfinance_preferences", MODE_PRIVATE);

                btn_save.setOnClickListener(v1 -> {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("Password", password1.getText().toString());
                    editor.apply();
                    Toast.makeText(SetPassword.this, R.string.password_saved, Toast.LENGTH_SHORT).show();
                    finish();
                });
            } else if (!password1.getText().toString().equals(password2.getText().toString())) {
                hint.setText(R.string.password_isnt_the_same);
            }else {
                hint.setText(R.string.password_hint1);
            }
        }
    }
}