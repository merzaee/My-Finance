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

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> al_l_id, al_c_name, al_l_amount, al_l_title, al_l_s_no;

    ListAdapter(Context context, ArrayList<String> al_l_id, ArrayList<String> c_name, ArrayList<String> al_l_amount, ArrayList<String> al_l_title, ArrayList<String> al_l_s_no) {
        this.context = context;
        this.al_l_id = al_l_id;
        this.al_c_name = c_name;
        this.al_l_amount = al_l_amount;
        this.al_l_title = al_l_title;
        this.al_l_s_no = al_l_s_no;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView l_id, l_title, l_amount, c_name, l_s_no;
        ConstraintLayout cl_list_row;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            l_id = itemView.findViewById(R.id.l_id);
            l_s_no = itemView.findViewById(R.id.tv_l_s_no);
            c_name = itemView.findViewById(R.id.tv_l_c_name);
            l_amount = itemView.findViewById(R.id.tv_l_amount);
            l_title = itemView.findViewById(R.id.tv_l_title);
            cl_list_row = itemView.findViewById(R.id.lists_row_id);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.lists_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.l_id.setText(String.valueOf(al_l_id.get(position)));
        holder.l_amount.setText(String.valueOf(al_l_amount.get(position)));
        holder.l_title.setText(String.valueOf(al_l_title.get(position)));
        holder.c_name.setText(String.valueOf(al_c_name.get(position)));
        holder.l_s_no.setText(String.valueOf(al_l_s_no.get(position)));

        holder.cl_list_row.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ListsDetail.class);
            intent.putExtra("l_id", al_l_id.get(position));
            intent.putExtra("c_name", al_c_name.get(position));
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return al_l_id.size();
    }


}

