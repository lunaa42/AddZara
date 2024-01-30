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
import com.squareup.picasso.Target;

import java.util.ArrayList;


public class ZaraAdapter extends RecyclerView.Adapter<ZaraAdapter.MyViewHolder>  {
        Context context;
        ArrayList<ZaraItem> zaList;
        private FirebaseServices fbs;
    private ZaraAdapter.OnItemClickListener itemClickListener;
        public ZaraAdapter(Context context, ArrayList<ZaraItem> zaList) {
            this.context = context;
            this.zaList = zaList;
            this.fbs = FirebaseServices.getInstance();
            this.itemClickListener = new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                /*
                String selectedItem = filteredList.get(position).getNameCar();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show(); */
                    Bundle args = new Bundle();
                    args.putParcelable("car", zaList.get(position)); // or use Parcelable for better performance
                    DetailsFragment pd = new DetailsFragment();
                    pd.setArguments(args);
                    FragmentTransaction ft= ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.Framelayoutmain4,pd);
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

    @Override
        public void onBindViewHolder(@NonNull ZaraAdapter.MyViewHolder holder, int position) {
        ZaraItem produ= zaList.get(position);
        User u = fbs.getCurrentUser();
        /*if (u != null)
        {
            if (u.getFavorites().contains(produ.get()))
                Picasso.get().load(R.drawable.favcheck).into(holder.ivFavourite);
            else
                Picasso.get().load(R.drawable.ic_fav).into(holder.ivFavourite);
        }*/
        holder.tvProduct.setText(produ.getProduct());
        holder.tvPrice.setText(produ.getPrice() + " ₪");
        holder.tvSize.setText(produ.getSize());
        holder.tvColour.setText(produ.getColour());
        holder.tvDescription.setText(produ.getDescription());
        holder.tvPrice.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        });
/*
        holder.carName.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.setOnItemClick(position);
            }
        }); */
        if (produ.getPhoto() == null || produ.getPhoto().isEmpty())
        {
            Picasso.get().load(R.drawable.clothingw5_removebg_preview).into(holder.ivphoto);
        }
        else {
            Picasso.get().load(produ.getPhoto()).into(holder.ivphoto);
        }
      /*  holder.ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite(car) == true)
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

        @Override
        public int getItemCount(){
            return zaList.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder{
            TextView  tvProduct,tvSize,tvColour,tvDescription,tvPrice;
            ImageView ivphoto;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvProduct=itemView.findViewById(R.id.etProductAddZaraFragment);
                tvSize=itemView.findViewById(R.id.etSIzeAddZaraFragment);
                tvColour=itemView.findViewById(R.id.etColourAddZaraFragment);
                tvDescription=itemView.findViewById(R.id.etDescriptionAddZaraFragment);
                tvPrice=itemView.findViewById(R.id.etPriceAddZaraFragment);
                ivphoto=itemView.findViewById(R.id.ivImgAddZaraFragment);

            }
        }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ZaraAdapter.OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
    }


