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

public class AddList extends AppCompatActivity implements View.OnClickListener {

    Button btn_save;
    EditText note, amount, list_date, title;
    Spinner contact;
    DBHelper dbHelper;
    private DatePickerDialog datePickerDialog;

    ArrayList<String> al_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        setTitle(R.string.addList);

        initiateView();
        initiateDatePicker();
    }

    private void initiateDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            String date = makeDateString(dayOfMonth, (month + 1), year);
            list_date.setText(date);
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

    private void initiateView() {
        btn_save = findViewById(R.id.btn_save_list);
        btn_save.setOnClickListener(this);
        note = findViewById(R.id.et_note);
        amount = findViewById(R.id.et_amount);
        list_date = findViewById(R.id.et_al_date);
        list_date.setShowSoftInputOnFocus(false);
        list_date.setFocusable(false);
        list_date.setOnClickListener(this);
        contact = findViewById(R.id.sp_contact);
        title = findViewById(R.id.et_list_title);
        dbHelper = new DBHelper(this);
        al_contact = new ArrayList<>();

        addContact_to_array();

        list_date.setText(get2DayDate());

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
        if (v.getId() == R.id.btn_save_list) {
            // now we can save the entered values for a new list
            if (check_condition()) {
                int end_index = contact.getSelectedItem().toString().indexOf(" ");
                int c_id = Integer.parseInt(contact.getSelectedItem().toString().substring(0, end_index));
                if (dbHelper.insertList(c_id, (Integer.parseInt(amount.getText().toString())), title.getText().toString(),
                        list_date.getText().toString(), note.getText().toString()) > 0) {
                    Toast.makeText(this, R.string.list_saved, Toast.LENGTH_SHORT).show();
                    Intent result_intent = new Intent();
                    setResult(200, result_intent);
                    finish();
                }
            } else {
                Toast.makeText(this, R.string.please_check_values, Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.et_al_date) {
            openDatePicker();
        }
    }

    private boolean check_condition() {
        return contact.getSelectedItem() != null && amount.getText().length() != 0;
    }
}