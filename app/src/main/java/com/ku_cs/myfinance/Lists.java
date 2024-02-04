package com.ku_cs.myfinance;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Lists extends AppCompatActivity implements View.OnClickListener{

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode()==200){
                    recreate();
                }
            }
    );


    FloatingActionButton fl_add_list;
    DBHelper dbHelper;
    ArrayList<String>  al_l_id, al_l_s_no, al_contact, al_amount, al_title;
    ListAdapter listsAdapter;
    RecyclerView list_rc;
    TextView tv_c_id;
    String page_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        setTitle(R.string.list);

        Intent intent = getIntent();
        dbHelper = new DBHelper(this);
        list_rc = findViewById(R.id.lists_rc);
        al_amount = new ArrayList<>();
        al_contact = new ArrayList<>();
        al_title = new ArrayList<>();
        al_l_s_no = new ArrayList<>();
        al_l_id = new ArrayList<>();
        tv_c_id= findViewById(R.id.tv_l_c_id);

        if (intent.getIntExtra("c_id", 0) > 0) {
            tv_c_id.setText(String.valueOf(intent.getIntExtra("c_id",0)));
            page_title = intent.getStringExtra("c_name");
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle(ab.getTitle() + ": " + page_title);
            }
        }

        fl_add_list = findViewById(R.id.fl_add_list);
        fl_add_list.setOnClickListener(this);


        display_lists();
        listsAdapter = new ListAdapter(Lists.this, al_l_id, al_contact, al_amount, al_title, al_l_s_no);

        list_rc.setAdapter(listsAdapter);

        list_rc.setLayoutManager(new LinearLayoutManager(Lists.this));

    }

    private void display_lists() {
        Cursor cursor;
        if (Integer.parseInt(tv_c_id.getText().toString()) > 0) {
            Log.d("tracing: ", "the id is: " + tv_c_id.getText().toString());
            cursor = dbHelper.getListVia_contact_id(Integer.parseInt(tv_c_id.getText().toString()));
            Log.d("tracing: ", "returned rows are " + cursor.getCount());
        } else {
            cursor = dbHelper.getAllListWithName();
        }

        if (cursor.getCount() == 0) {
            Toast.makeText(this, R.string.no_data, Toast.LENGTH_SHORT).show();
        } else {
            int counter = 1;
            while (cursor.moveToNext()) {
                al_l_s_no.add(String.valueOf(counter));
                al_contact.add(cursor.getString(1));
                al_amount.add(cursor.getString(2));
                al_title.add(cursor.getString(4));
                al_l_id.add(cursor.getString(5));
                counter++;
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Lists.this, AddList.class);
        i.putExtra("c_id", tv_c_id.getText().toString());
        activityResultLauncher.launch(i);
    }
}