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

public class Payments extends AppCompatActivity implements View.OnClickListener {

    TextView tv_c_id;
    String title;
    FloatingActionButton btn_add_payments;
    RecyclerView rv_payments;
    DBHelper dbHelper;
    ArrayList<String> al_p_id, al_p_s_no, al_contact, al_amount;
    PaymentAdapter paymentAdapter;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode()==300){
                    recreate();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        setTitle(R.string.payments);

        initiateViews();

    }

    private void initiateViews() {
        Intent i = getIntent();
        tv_c_id = findViewById(R.id.tv_p_c_id);
        btn_add_payments = findViewById(R.id.fl_add_payments);
        rv_payments = findViewById(R.id.rv_payments);
        btn_add_payments.setOnClickListener(this);
        dbHelper = new DBHelper(this);
        rv_payments = findViewById(R.id.rv_payments);
        al_amount = new ArrayList<>();
        al_contact = new ArrayList<>();
        al_p_s_no = new ArrayList<>();
        al_p_id = new ArrayList<>();
        if (i.getIntExtra("c_id", 0) > 0) {
            tv_c_id.setText(String.valueOf(i.getIntExtra("c_id", 0)));
            title = i.getStringExtra("c_name");
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle(ab.getTitle() + ": " + title);
            }
        }

        display_payments();
        paymentAdapter = new PaymentAdapter(this, al_p_id, al_contact, al_amount, al_p_s_no);
        rv_payments.setAdapter(paymentAdapter);

        rv_payments.setLayoutManager(new LinearLayoutManager(Payments.this));
    }

    private void display_payments() {
        Cursor cursor;
        if (Integer.parseInt(tv_c_id.getText().toString()) > 0) {
            cursor = dbHelper.getPayments(Integer.parseInt(tv_c_id.getText().toString()));

        } else {
            cursor = dbHelper.getPayments();
            Log.d("check_value", "after getting all data");
        }
        if (cursor.getCount() == 0) {
            Log.d("check_value", "count is 0");
            Toast.makeText(this, R.string.no_data, Toast.LENGTH_SHORT).show();
        } else {
            Log.d("check_value", "before while and the count is " + cursor.getCount());
            int counter = 1;
            //how to change the null value to another
            while (cursor.moveToNext()) {
                Log.d("check_value", "inside loop");
                al_p_s_no.add(String.valueOf(counter));
                al_contact.add(cursor.getString(1));
                al_amount.add(cursor.getString(2));
                al_p_id.add(cursor.getString(5));
                counter++;
                Log.d("check_value", "loop is done");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fl_add_payments) {
            Intent intent = new Intent(this, AddPayment.class);
            intent.putExtra("c_id", "0");
            intent.putExtra("c_id", tv_c_id.getText().toString());
            activityResultLauncher.launch(intent);
        }
    }
}