package com.example.addzara;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ZaraAdapter extends RecyclerView.Adapter<ZaraAdapter.MyViewHolder> {
    private Context context;
    private List<ZaraItem> zaList;
    private OnItemClickListener itemClickListener;

    public ZaraAdapter(Context context, List<ZaraItem> zaList) {
        this.context = context;
        this.zaList = zaList;
    }

    public void setZaraItems(List<ZaraItem> zaraItems) {
        this.zaList = zaraItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.zara_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ZaraItem zaraItem = zaList.get(position);
        holder.bind(zaraItem);
    }

    @Override
    public int getItemCount() {
        return zaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameTextView;
        private ImageView productImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.tvProductName);
            productImageView = itemView.findViewById(R.id.ivzaraItem); // Initialize ImageView

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            itemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bind(ZaraItem item) {
            productNameTextView.setText(item.getProduct());

            // Load image using a library like Picasso or Glide
            //if (item.getPhoto() != null)
                //Picasso.get().load(item.getPhoto()).into(productImageView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
