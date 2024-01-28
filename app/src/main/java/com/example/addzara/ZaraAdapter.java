package com.example.addzara;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class ZaraAdapter extends RecyclerView.Adapter<ZaraAdapter.MyViewHolder> {
        Context context;
    ArrayList<ZaraItem> zaList;
    private ZaraAdapter.OnItemClickListener itemClickListener;
    private FirebaseServices fbs;

        public ZaraAdapter(Context context, ArrayList<ZaraItem> carsList) {
            this.context = context;
            this.zaList = zaList;
            this.fbs = FirebaseServices.getInstance();
            this.itemClickListener = new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Bundle args = new Bundle();
                    args.putParcelable("product", zaList.get(position)); // or use Parcelable for better performance
                    DetailsFragment de = new DetailsFragment();
                    de.setArguments(args);
                    FragmentTransaction ft= ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.Framelayoutmain4,de);
                    ft.commit();
                }
            } ;
        }

        @NonNull
        @Override
        public ZaraAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            View v= LayoutInflater.from(context).inflate(R.layout.zara_item,parent,false);
            return  new ZaraAdapter.MyViewHolder(v);
        }

    public void setOnItemClickListener(ZaraAdapter.OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnImageClickListener {
        void onImageClick(Image image);
    }

    @Override
        public void onBindViewHolder(@NonNull ZaraAdapter.MyViewHolder holder, int position) {
            ZaraItem za = zaList.get(position);
            User u = fbs.getCurrentUser();
            holder.tvProduct.setText(za.getProduct());
            holder.tvColour.setText(za.getColour());
            holder.tvSize.setText(za.getSize());
            holder.tvPrice.setText(za.getPrice() + " ₪");
            holder.tvdescription.setText(za.getDescription());
/*
 if (u != null)
        {
            if (u.getFavorites().contains(car.getId()))
                Picasso.get().load(R.drawable.favcheck).into(holder.ivFavourite);
            else
                Picasso.get().load(R.drawable.ic_fav).into(holder.ivFavourite);
        }
            if (za.getPhoto() == null || za.getPhoto().isEmpty())
    {
        Picasso.get().load(R.drawable.ic_fav).into(holder.ivCar);
    }
        else {
        Picasso.get().load(car.getPhoto()).into(holder.ivCar);
    }
        holder.ivFavourite.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isFavorite(car) == true)                                                                    fav thing
            {
                removeStar(car, holder);
            }
            else
            {
                addStar(car, holder);
            }
            fbs.setUserChangeFlag(true);
            //setFavourite(holder, car);
        }
    });*/

    }
   /* private void removeStar(CarItem car, CarListAdapter2.MyViewHolder holder) {
        User u = fbs.getCurrentUser();
        if (u != null) {
            if (u.getFavorites().contains(car.getId())) {
                u.getFavorites().remove(car.getId());
                holder.ivFavourite.setImageResource(android.R.color.transparent);
                Picasso.get().load(R.drawable.ic_fav).into(holder.ivFavourite);
            }
        }
    }                                                                                           fav thing

    private void addStar(CarItem car, CarListAdapter2.MyViewHolder holder) {
        User u = fbs.getCurrentUser();
        if (u != null) {
            u.getFavorites().add(car.getId());
            holder.ivFavourite.setImageResource(android.R.color.transparent);
            Picasso.get().load(R.drawable.favcheck).into(holder.ivFavourite);
        }
    }

    private boolean isFavorite(CarItem car) {
        User u = fbs.getCurrentUser();
        if (u != null)
        {
            if (u.getFavorites().contains(car.getId()))
                return true;
        }
        return false;
    }*/

        @Override
        public int getItemCount(){
            return zaList.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder{
            TextView tvProduct, tvColour,tvSize,tvPrice,tvdescription;
            ImageView ivProduct;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvProduct=itemView.findViewById(R.id.tvproduct2Deta);
                tvColour=itemView.findViewById(R.id.tvcolour2Deta);
                tvSize=itemView.findViewById(R.id.tvsize2Deta);
                tvPrice=itemView.findViewById(R.id.tvprice2Deta);
                tvdescription=itemView.findViewById(R.id.tvdescri2Deta);
                ivProduct=itemView.findViewById(R.id.ivProductdeta);
            }
        }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }


}


