package com.ku_cs.myfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {
    Button btn_save;
    EditText name, phone, address;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        setTitle(R.string.addContact);
        initiateViews();

        btn_save.setOnClickListener(v ->
        {
            String c_name, c_phone, c_address;
            c_name = name.getText().toString();
            c_phone = phone.getText().toString();
            c_address = address.getText().toString();
            if (c_name.length() > 2) {
                if (dbHelper.insertContact(c_name, c_phone, c_address) > 0) {
                    Toast.makeText(AddContact.this, getString(R.string.contact_saved), Toast.LENGTH_SHORT).show();
                   //contact inserted, so we have to notify parent activity
                    Intent result = new Intent();
                    setResult(100, result);
                   // passing this new contact Id to contacts_detail page
                    Cursor cursor = dbHelper.getLastContact();
                    cursor.moveToNext();
                    Intent intent = new Intent(AddContact.this, ContactDetails.class);
                    intent.putExtra("c_id", cursor.getString(0));
                    intent.putExtra("contact_name", cursor.getString(1));
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddContact.this, "currently we can not save your data", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AddContact.this, getString(R.string.fill_accurately), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initiateViews() {
        btn_save = findViewById(R.id.btn_save);
        name = findViewById(R.id.etName);
        phone = findViewById(R.id.etPhone_no);
        address = findViewById(R.id.etAddress);
        dbHelper = new DBHelper(this);
    }
}