package com.example.addzara;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class ZaraAdapter extends RecyclerView.Adapter implements ZaraAdapter1 {
        Context context;
        ArrayList<Zara> zaList;
        private FirebaseServices fbs;

        public ZaraAdapter(Context context, ArrayList<Zara> zaList) {
            this.context = context;
            this.zaList = zaList;
            this.fbs = FirebaseServices.getInstance();
        }

        @NonNull
        @Override
        public ZaraAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            View v= LayoutInflater.from(context).inflate(R.layout.zara_item,parent,false);
            return  new ZaraAdapter.MyViewHolder(v);
        }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
        public void onBindViewHolder(@NonNull ZaraAdapter.MyViewHolder holder, int position) {
            Zara za = zaList.get(position);
            holder.tvProduct.setText(za.getProduct());
            holder.tvPhone.setText(za.getPhone());
        }

        @Override
        public int getItemCount(){
            return zaList.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder{
            TextView tvPhone, tvProduct;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvProduct=itemView.findViewById(R.id.tvProductZaraItem);
                tvPhone=itemView.findViewById(R.id.tvPhoneZaraItem);

            }
        }
    }


