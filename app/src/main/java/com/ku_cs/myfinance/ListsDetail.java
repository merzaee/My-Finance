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

public class ListsDetail extends AppCompatActivity implements View.OnClickListener {

    int l_id;
    EditText contact, et_l_id, l_date, l_title, l_note, l_amount;
    String c_name;
    Button btn_delete, btn_save;
    DBHelper dbHelper;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists_detail);
        setTitle(R.string.list_detail);

        initiateViews();
        display_data();
        avoid_keyboard();
        initiateDatePicker();
    }

    private void initiateViews() {
        Intent intent = getIntent();
        l_id = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("l_id")));
        c_name = intent.getStringExtra("c_name");
        contact = findViewById(R.id.et_ld_contact);
        l_date = findViewById(R.id.et_ld_date);
        l_title = findViewById(R.id.et_ld_title);
        l_note = findViewById(R.id.et_ld_note);
        l_amount = findViewById(R.id.et_ld_amount);
        btn_delete = findViewById(R.id.btn_ld_delete);
        btn_save = findViewById(R.id.btn_ld_update);
        btn_delete.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        l_date.setShowSoftInputOnFocus(false);
        l_date.setInputType(InputType.TYPE_NULL);
        et_l_id = findViewById(R.id.et_ld_l_id);
        et_l_id.setText(String.valueOf(l_id));
        l_date.setOnClickListener(this);
    }

    private void display_data() {
        contact.setText(c_name);
        dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getLists(l_id);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(ab.getTitle() + ": ID# " + l_id);
        }
        cursor.moveToNext();
        l_amount.setText(cursor.getString(2));
        l_title.setText(cursor.getString(3));
        l_date.setText(cursor.getString(4));
        l_note.setText(cursor.getString(5));
    }

    @Override
    public void onClick(View v) {
        String title, message;
        if (v.getId() == R.id.btn_ld_delete) {
            title = getString(R.string.delete) + " " + getString(R.string.list) + " #" + l_id;
            message = getString(R.string.confirm_list_delete) + " #" + l_id + " ?";
            confirm_dialogue(title, message, "delete");
        } else if (v.getId() == R.id.btn_ld_update) {
            // here is the place to save changes
            title = getString(R.string.update) + " " + getString(R.string.list) + " #" + l_id;
            message = getString(R.string.confirm_list_changes) + " #" + l_id + " ?";
            confirm_dialogue(title, message, "update");
        } else if (v.getId() == R.id.et_ld_date) {
            openDatePicker();
        }
    }

    public void confirm_dialogue(String title, String message, String operation) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            if (operation.equals("delete")) {
                if (dbHelper.delete_list(l_id) != -1) {
                    Toast.makeText(this, getString(R.string.list) + " " + getString(R.string.deleted), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "list didn't deleted, please try again later", Toast.LENGTH_SHORT).show();
                }
            } else if (operation.equals("update")) {
                if (dbHelper.update_list(l_id, Integer.parseInt(l_amount.getText().toString()), l_date.getText().toString(), l_title.getText().toString(), l_note.getText().toString()) > 0) {
                    Toast.makeText(this, R.string.list_updated, Toast.LENGTH_SHORT).show();
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
            l_date.setText(date);
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
        et_l_id.setShowSoftInputOnFocus(false);
        et_l_id.setFocusable(false);
        et_l_id.setInputType(InputType.TYPE_NULL);

        contact.setShowSoftInputOnFocus(false);
        contact.setFocusable(false);
        contact.setInputType(InputType.TYPE_NULL);
    }

}