package com.ku_cs.myfinance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> al_c_id, al_c_name, al_c_money, al_c_phone, al_c_s_no;

    ContactsAdapter(Context context, ArrayList<String> c_id, ArrayList<String> c_name, ArrayList<String> c_money, ArrayList<String> c_phone, ArrayList<String> al_c_s_no) {
        this.context = context;
        this.al_c_id = c_id;
        this.al_c_name = c_name;
        this.al_c_money = c_money;
        this.al_c_phone = c_phone;
        this.al_c_s_no = al_c_s_no;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView c_id, c_name, c_money, c_phone, c_s_no;
        ConstraintLayout cr_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            c_s_no = itemView.findViewById(R.id.tv_s_no);
            c_name = itemView.findViewById(R.id.tv_c_name);
            c_money = itemView.findViewById(R.id.tv_c_money);
            c_phone = itemView.findViewById(R.id.tv_c_phone);
            cr_layout = itemView.findViewById(R.id.cr_layout);
            c_id = itemView.findViewById(R.id.tv_cr_id);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.contacts_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.c_id.setText(String.valueOf(al_c_id.get(position)));
        holder.c_s_no.setText(String.valueOf(al_c_s_no.get(position)));
        holder.c_name.setText(String.valueOf(al_c_name.get(position)));
        holder.c_money.setText(String.valueOf(al_c_money.get(position)));
        holder.c_phone.setText(String.valueOf(al_c_phone.get(position)));
            //
        if (Double.parseDouble(al_c_money.get(position)) < 0){
            holder.c_money.setTextColor(Color.parseColor("#DA1623"));
        }
        holder.cr_layout.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ContactDetails.class);
            intent.putExtra("c_id", al_c_id.get(position));
            intent.putExtra("contact_name", al_c_name.get(position));
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return al_c_id.size();
    }

}

