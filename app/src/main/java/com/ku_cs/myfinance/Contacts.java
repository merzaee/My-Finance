package com.ku_cs.myfinance;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {

    FloatingActionButton add_contact;
    DBHelper dbHelper;
    ArrayList<String> al_c_id, al_c_name, al_c_money, al_c_phone, al_s_no;
    ContactsAdapter contactsAdapter;
    RecyclerView contacts_rc;
    ImageView noDataImg;
    TextView noData;
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == 100) {
                    recreate();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setTitle(R.string.contacts);

        add_contact = findViewById(R.id.fl_btn_add_contact);
        dbHelper = new DBHelper(this);
        contacts_rc = findViewById(R.id.contacts_rc);
        al_c_id = new ArrayList<>();
        al_c_name = new ArrayList<>();
        al_c_phone = new ArrayList<>();
        al_c_money = new ArrayList<>();
        al_s_no = new ArrayList<>();
        noData = findViewById(R.id.tv_nodata);
        noDataImg = findViewById(R.id.nodataimage);

        display_contacts();
        contactsAdapter = new ContactsAdapter(Contacts.this, al_c_id, al_c_name, al_c_money, al_c_phone, al_s_no);
        contacts_rc.setAdapter(contactsAdapter);
        contacts_rc.setLayoutManager(new LinearLayoutManager(Contacts.this));

        add_contact.setOnClickListener(v -> {
            Intent intent = new Intent(Contacts.this, AddContact.class);
            activityResultLauncher.launch(intent);
        });

    }

    private void display_contacts() {
        Cursor cursor = dbHelper.getContactDetail();
        if (cursor.getCount() == 0) {
            noDataImg.setVisibility(View.VISIBLE);
            noData.setVisibility(View.VISIBLE);
        } else {
            int counter = 1;
            while (cursor.moveToNext()) {
                double reminder;
                int listed = 0, payed = 0;
                if (cursor.getString(4) != null) {
                    listed = Integer.parseInt(cursor.getString(4));
                }
                if (cursor.getString(5) != null) {
                    payed = Integer.parseInt(cursor.getString(5));
                }
                reminder = listed - payed;
                al_s_no.add(String.valueOf(counter));
                al_c_id.add(cursor.getString(0));
                al_c_name.add(cursor.getString(1));
                al_c_phone.add(cursor.getString(2));
                al_c_money.add(String.valueOf(reminder));
                counter++;
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        int item_id = item.getItemId();
        if (item_id == R.id.mnu_payments) {
            intent = new Intent(Contacts.this, Payments.class);
        } else if (item_id == R.id.mnu_lists) {
            intent = new Intent(Contacts.this, Lists.class);
        } else if (item_id == R.id.mnu_password) {
            intent = new Intent(Contacts.this, UpdatePassword.class);
        } else if (item_id == R.id.mnu_about) {
            intent = new Intent(Contacts.this, About.class);
        } else if (item_id == R.id.mnu_summary) {
            intent = new Intent(Contacts.this, Summary.class);
        } else {
            return false;
        }
        startActivity(intent);
        return true;
    }

}