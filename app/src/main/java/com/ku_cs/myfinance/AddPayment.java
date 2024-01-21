package com.ku_cs.myfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AddPayment extends AppCompatActivity implements View.OnClickListener{

    EditText  et_amount, et_date, et_note;
    Spinner contact;
    DBHelper dbHelper;
    ArrayList<String> al_contact;
    Button btn_save;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        InitiateViews();
    }

    private void InitiateViews() {
        contact = findViewById(R.id.sp_p_contact);
        et_amount = findViewById(R.id.et_p_amount);
        et_date = findViewById(R.id.et_p_date);
        et_date.setShowSoftInputOnFocus(false);
        et_date.setFocusable(false);
        et_date.setOnClickListener(this);
        et_note = findViewById(R.id.et_p_note);
        dbHelper = new DBHelper(this);
        al_contact = new ArrayList<>();
        btn_save = findViewById(R.id.btn_p_save);
        btn_save.setOnClickListener(this);
        addContact_to_array();
        initiateDatePicker();
        et_date.setText(get2DayDate());
        contact.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, al_contact));

    }

    private void addContact_to_array() {
        Intent i = getIntent();
        Cursor cursor = dbHelper.getContacts();
        int c_id = Integer.parseInt(Objects.requireNonNull(i.getStringExtra("c_id")));
        if ( c_id == 0) {
            if (dbHelper.getContactDetail().getCount() > 0) {
                cursor = dbHelper.getContactDetail();
            }
        }else {
            cursor = dbHelper.getContactDetail(c_id);
        }
        while (cursor.moveToNext()) {
            al_contact.add(cursor.getString(0).concat(" - ").concat(cursor.getString(1)));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_p_save) {
            // now we can save the entered values for a new list
            if (check_condition()) {
                int end_index = contact.getSelectedItem().toString().indexOf(" ");
                int c_id = Integer.parseInt(contact.getSelectedItem().toString().substring(0, end_index));
                if (dbHelper.insertPayment(c_id, (Integer.parseInt(et_amount.getText().toString())),
                        et_date.getText().toString(), et_note.getText().toString()) > 0) {
                    Toast.makeText(this, R.string.payment_saved, Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                Toast.makeText(this, R.string.please_check_values, Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.et_p_date) {
            openDatePicker();
        }
    }
    private boolean check_condition() {
        return contact.getSelectedItem() != null && et_amount.getText().length() != 0;
    }

    private void initiateDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            String date = makeDateString(dayOfMonth, (month + 1), year);
            et_date.setText(date);
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return year + "/" + month + "/" + dayOfMonth;
    }

    public void openDatePicker() {
        datePickerDialog.show();
    }

    String get2DayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month += 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

}