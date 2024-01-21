package com.ku_cs.myfinance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> al_p_id, al_c_name, al_p_amount, al_p_s_no;

    PaymentAdapter(Context context, ArrayList<String> al_p_id, ArrayList<String> al_c_name, ArrayList<String> al_p_amount, ArrayList<String> al_p_s_no) {
        this.context = context;
        this.al_p_s_no = al_p_s_no;
        this.al_c_name = al_c_name;
        this.al_p_amount = al_p_amount;
        this.al_p_id = al_p_id;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView p_id, p_amount, c_name, p_s_no;
        ConstraintLayout cl_pays_row;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            p_id = itemView.findViewById(R.id.tv_p_id);
            p_amount = itemView.findViewById(R.id.tv_p_amount);
            c_name = itemView.findViewById(R.id.tv_p_c_name);
            p_s_no = itemView.findViewById(R.id.tv_p_s_no);
            cl_pays_row = itemView.findViewById(R.id.pays_row_id);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.payments_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.p_s_no.setText(String.valueOf(al_p_s_no.get(position)));
        holder.p_id.setText(String.valueOf(al_p_id.get(position)));
        holder.p_amount.setText(String.valueOf(al_p_amount.get(position)));
        holder.c_name.setText(String.valueOf(al_c_name.get(position)));

        holder.cl_pays_row.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PaymentDetail.class);
            intent.putExtra("p_id", al_p_id.get(position));
            intent.putExtra("c_name", al_c_name.get(position));
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return al_p_id.size();
    }


}

