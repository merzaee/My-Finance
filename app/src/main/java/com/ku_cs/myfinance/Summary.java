package com.ku_cs.myfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;

import android.widget.EditText;

public class Summary extends AppCompatActivity {

    EditText total_counts, total_listed, total_payment, total_reminder;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        setTitle(getString(R.string.summary));

        initiateViews();
        avoid_keyboard();
        dbHelper = new DBHelper(this);

        Cursor c_listed = dbHelper.getTotalListed();
        c_listed.moveToNext();
        Long listed = c_listed.getLong(0);

        Cursor c_payed = dbHelper.getTotalPayment();
        c_payed.moveToNext();
        Long payed = c_payed.getLong(0);

        int counts = dbHelper.getContacts().getCount();

        total_counts.setText(String.valueOf(counts));
        total_payment.setText(String.valueOf(payed));
        total_listed.setText(String.valueOf(listed));
        total_reminder.setText(String.valueOf((listed - payed)));

        if (listed - payed < 0){
            total_reminder.setTextColor(Color.parseColor("#DA1623"));
        }

    }

    private void initiateViews() {
        total_counts = findViewById(R.id.et_summary_contact_count);
        total_listed = findViewById(R.id.et_summary_total_listed);
        total_payment = findViewById(R.id.et_summary_total_payment);
        total_reminder = findViewById(R.id.et_summary_reminder);
    }

    public void avoid_keyboard() {
        total_reminder.setShowSoftInputOnFocus(false);
        total_reminder.setFocusable(false);
        total_reminder.setInputType(InputType.TYPE_NULL);

        total_counts.setShowSoftInputOnFocus(false);
        total_counts.setFocusable(false);
        total_counts.setInputType(InputType.TYPE_NULL);

        total_listed.setShowSoftInputOnFocus(false);
        total_listed.setFocusable(false);
        total_listed.setInputType(InputType.TYPE_NULL);

        total_payment.setShowSoftInputOnFocus(false);
        total_payment.setFocusable(false);
        total_payment.setInputType(InputType.TYPE_NULL);

    }
}