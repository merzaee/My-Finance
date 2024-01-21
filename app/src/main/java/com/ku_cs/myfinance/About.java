package com.ku_cs.myfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class About extends AppCompatActivity implements View.OnClickListener{

    TextView version;
    EditText developer, email, organization, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(R.string.about);

        version = findViewById(R.id.tv_a_app_version);
        developer = findViewById(R.id.et_a_app_developer);
        email = findViewById(R.id.et_a_email);
        organization = findViewById(R.id.et_a_organization);
        phone = findViewById(R.id.et_a_phone);

        version.setText(version.getText().toString().concat(": 1.0" ));
        developer.setText(R.string.mmm);
        email.setText(R.string.developer_email);
        organization.setText(R.string.ku_csf);
        phone.setText(R.string.developer_phone);

        avoid_keyboard();
        developer.setOnClickListener(this);
        email.setOnClickListener(this);
        phone.setOnClickListener(this);
        developer.setTextColor(Color.parseColor("#0707A3"));
        email.setTextColor(Color.parseColor("#0707A3"));
        phone.setTextColor(Color.parseColor("#0707A3"));

    }

    public void avoid_keyboard() {
        developer.setShowSoftInputOnFocus(false);
        developer.setFocusable(false);
        developer.setInputType(InputType.TYPE_NULL);

        email.setShowSoftInputOnFocus(false);
        email.setFocusable(false);
        email.setInputType(InputType.TYPE_NULL);

        phone.setShowSoftInputOnFocus(false);
        phone.setFocusable(false);
        phone.setInputType(InputType.TYPE_NULL);

        organization.setShowSoftInputOnFocus(false);
        organization.setFocusable(false);
        organization.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId()==R.id.et_a_phone){
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+93796175647"));
        } else if (v.getId()==R.id.et_a_email) {
            Uri uri = Uri.parse("mailto:mjmerzaee@gmail.com");
            intent = new Intent(Intent.ACTION_SENDTO, uri);
        }else{
            intent = new Intent(Intent.ACTION_VIEW);
            String uri = "https://www.linkedin.com/in/mjmerzaee";
            intent.setData(Uri.parse(uri));
        }
        startActivity(intent);
    }
}