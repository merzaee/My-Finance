package com.ku_cs.myfinance;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

public class PaymentDetail extends AppCompatActivity implements View.OnClickListener {

    String title;
    int p_id;
    EditText contact, et_p_id, p_date, p_note, p_amount;
    String c_name;
    Button btn_delete, btn_save;
    DBHelper dbHelper;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        initiateViews();
        display_data();
        avoid_keyboard();
        initiateDatePicker();
    }

    private void initiateViews() {
        title = getString(R.string.payment_detail);
        Intent intent = getIntent();
        if (!Objects.requireNonNull(intent.getStringExtra("c_name")).isEmpty()) {
            title = title + ": " + intent.getStringExtra("c_name");
            p_id = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("p_id")));
            c_name = intent.getStringExtra("c_name");
        }
        setTitle(title);
        contact = findViewById(R.id.et_pd_contact);
        et_p_id = findViewById(R.id.et_pd_p_id);
        p_date = findViewById(R.id.et_pd_date);
        p_note = findViewById(R.id.et_pd_note);
        p_amount = findViewById(R.id.et_pd_amount);
        btn_delete = findViewById(R.id.btn_pd_delete);
        btn_save = findViewById(R.id.btn_pd_update);
        btn_delete.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        et_p_id.setText(String.valueOf(p_id));
        p_date.setOnClickListener(this);
    }

    private void display_data() {
        contact.setText(c_name);
        dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getPayment(p_id);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(ab.getTitle() + ": ID# " + p_id);
        }
        cursor.moveToNext();
        et_p_id.setText(String.valueOf(p_id));
        p_amount.setText(cursor.getString(2));
        p_date.setText(cursor.getString(3));
        p_note.setText(cursor.getString(4));
    }

    @Override
    public void onClick(View v) {
        String title, message;
        if (v.getId() == R.id.btn_pd_delete) {
            title = getString(R.string.delete) + " " + getString(R.string.payments) + " #" + p_id;
            message = getString(R.string.confirm_list_delete) + " #" + p_id + " ?";
            confirm_dialogue(title, message, "delete");
        } else if (v.getId() == R.id.btn_pd_update) {
            // here is the place to save changes
            title = getString(R.string.update) + " " + getString(R.string.payments) + " #" + p_id;
            message = getString(R.string.confirm_list_changes) + " #" + p_id + " ?";
            confirm_dialogue(title, message, "update");
        } else if (v.getId() == R.id.et_pd_date) {
            openDatePicker();
        }
    }

    public void confirm_dialogue(String title, String message, String operation) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            if (operation.equals("delete")) {
                if (dbHelper.delete_payment(p_id) != -1) {
                    Toast.makeText(this, getString(R.string.payments) + " " + getString(R.string.deleted), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "payments didn't deleted, please try again later", Toast.LENGTH_SHORT).show();
                }
            } else if (operation.equals("update")) {
                if (dbHelper.update_payment(p_id, Integer.parseInt(p_amount.getText().toString()), p_date.getText().toString(), p_note.getText().toString()) > 0) {
                    Toast.makeText(this, R.string.payment_updated, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void initiateDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            String date = makeDateString(dayOfMonth, (month + 1), year);
            p_date.setText(date);
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return year + "/" + month + "/" + dayOfMonth;
    }

    public void openDatePicker() {
        datePickerDialog.show();
    }

    public void avoid_keyboard() {
        et_p_id.setShowSoftInputOnFocus(false);
        et_p_id.setFocusable(false);
        et_p_id.setInputType(InputType.TYPE_NULL);

        contact.setShowSoftInputOnFocus(false);
        contact.setFocusable(false);
        contact.setInputType(InputType.TYPE_NULL);

        p_date.setShowSoftInputOnFocus(false);
        p_date.setInputType(InputType.TYPE_NULL);
    }
}