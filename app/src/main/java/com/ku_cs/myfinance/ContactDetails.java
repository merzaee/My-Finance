package com.ku_cs.myfinance;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class ContactDetails extends AppCompatActivity implements View.OnClickListener {

    int c_id = 0;
    int list_count = 0, payments_count = 0;
    EditText et_c_id, c_name, c_phone, c_address, total_listed, total_payment, total_reminder;
    Button btn_delete, btn_lists, btn_payments;
    DBHelper dbHelper;
    Cursor cursor;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        title = "Contact Detail";
        initiateViews();
    }

    private void initiateViews() {
        Intent intent = getIntent();
        if (intent.getStringExtra("c_id") != null) {
            c_id = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("c_id")));
            title = intent.getStringExtra("contact_name");
        }
        et_c_id = findViewById(R.id.ed_cd_id);
        et_c_id.setText(String.valueOf(c_id));
        c_name = findViewById(R.id.ed_cd_name);
        c_phone = findViewById(R.id.ed_cd_phone);
        c_address = findViewById(R.id.ed_cd_address);
        total_listed = findViewById(R.id.ed_cd_total_listed);
        total_payment = findViewById(R.id.ed_cd_payments);
        total_reminder = findViewById(R.id.ed_cd_reminder);
        btn_delete = findViewById(R.id.btn_cd_delete);
        btn_delete.setOnClickListener(this);
        btn_payments = findViewById(R.id.btn_cd_payments);
        btn_payments.setOnClickListener(this);
        btn_lists = findViewById(R.id.btn_cd_lists);
        btn_lists.setOnClickListener(this);
        avoid_keyboard();
        display_contacts_detail();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }
    }

    private void display_contacts_detail() {
        dbHelper = new DBHelper(this);
        cursor = dbHelper.getContactDetail(c_id);
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            double reminder;
            int listed = 0, payed = 0;
            if (cursor.getString(4) != null) {
                listed = Integer.parseInt(cursor.getString(4));
            }
            if (cursor.getString(5) != null) {
                payed = Integer.parseInt(cursor.getString(5));
            }
            reminder = listed - payed;
            c_name.setText(cursor.getString(1));
            c_phone.setText(cursor.getString(2));
            c_address.setText(cursor.getString(3));
            total_listed.setText(String.valueOf(listed));
            total_payment.setText(String.valueOf(payed));
            total_reminder.setText(String.valueOf(reminder));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cd_delete) {
            list_count = dbHelper.get_lists_count_via_contact_id(c_id).getCount();
            payments_count = dbHelper.get_payments_count_via_contact_id(c_id).getCount();
            confirm_delete(list_count + " lists and " + payments_count + " payments will be deleted");
        } else if (v.getId() == R.id.btn_cd_lists) {
            Intent intent = new Intent(v.getContext(), Lists.class);
            intent.putExtra("c_id", c_id);
            intent.putExtra("c_name", c_name.getText().toString());
            v.getContext().startActivity(intent);
        } else if (v.getId()==R.id.btn_cd_payments) {
            Intent intent = new Intent(v.getContext(), Payments.class);
            intent.putExtra("c_id", c_id);
            intent.putExtra("c_name", c_name.getText().toString());
            v.getContext().startActivity(intent);
        }
    }

    public void confirm_delete(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Contact?");
        builder.setMessage(message);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            if (list_count > 0) {
                dbHelper.delete_lists_via_contact_id(c_id);
            }
            if (payments_count > 0) {
                dbHelper.delete_payments_via_contact_id(c_id);
            }
            if (dbHelper.delete_contact(c_id) != -1) {
                Toast.makeText(ContactDetails.this, " contact deleted", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ContactDetails.this, "contact did not deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void avoid_keyboard() {
        et_c_id.setShowSoftInputOnFocus(false);
        et_c_id.setFocusable(false);
        et_c_id.setInputType(InputType.TYPE_NULL);

        total_reminder.setShowSoftInputOnFocus(false);
        total_reminder.setFocusable(false);
        total_reminder.setInputType(InputType.TYPE_NULL);

        total_payment.setShowSoftInputOnFocus(false);
        total_payment.setFocusable(false);
        total_payment.setInputType(InputType.TYPE_NULL);

        total_listed.setShowSoftInputOnFocus(false);
        total_listed.setFocusable(false);
        total_listed.setInputType(InputType.TYPE_NULL);

        c_name.setShowSoftInputOnFocus(false);
        c_name.setFocusable(false);
        c_name.setInputType(InputType.TYPE_NULL);
    }
}