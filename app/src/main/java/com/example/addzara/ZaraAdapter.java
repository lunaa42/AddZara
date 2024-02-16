package com.example.addzara;

import static com.google.android.material.R.drawable.ic_arrow_back_black_24;

import android.content.Context;
import android.media.ExifInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ZaraAdapter extends RecyclerView.Adapter<ZaraAdapter.MyViewHolder> {
    private Context context;
    private List<ZaraItem> zaList;
    private ZaraAdapter.OnItemClickListener itemClickListener;
    private FirebaseServices fbs;

    public ZaraAdapter(Context context, List<ZaraItem> zaList) {
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
                args.putParcelable("products", zaList.get(position)); // or use Parcelable for better performance
                DetailsFragment cd = new DetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft= ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.Framelayoutmain4,cd);
                ft.commit();
            }

            @Override
            public void onItemClick(ZaraItem clickedItem) {

            }
        } ;
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

        // Calculate the index of the first product in the pair
        int firstProductIndex = position * 2;

        // Check if the first product index is within bounds
        if (firstProductIndex < zaList.size()) {
            // Get the first product in the pair
            ZaraItem firstProduct = zaList.get(firstProductIndex);
            // Bind data for the first product
            holder.bindProduct1(firstProduct);
        }

        // Check if there is a second product in the pair
        if (firstProductIndex + 1 < zaList.size()) {
            // Get the second product in the pair
            ZaraItem secondProduct = zaList.get(firstProductIndex + 1);
            // Bind data for the second product
            holder.bindProduct2(secondProduct);
        } else {
            // If there is no second product, hide the views for the second product
            holder.hideSecondProduct();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    ZaraItem clickedItem = zaList.get(clickedPosition);
                    itemClickListener.onItemClick(clickedItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil(zaList.size() / 2.0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameTextView1;
        private ImageView productImageView1;
        private TextView productNameTextView2;
        private ImageView productImageView2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView1 = itemView.findViewById(R.id.tvProductName1);
            productImageView1 = itemView.findViewById(R.id.ivzaraItem1);
            productNameTextView2 = itemView.findViewById(R.id.tvProductName2);
            productImageView2 = itemView.findViewById(R.id.ivzaraItem2);
           /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                        itemClickListener.onItemClick(position);
                    }
                }
            });*/
        }


        public void bindProduct1(ZaraItem product) {
            if (productNameTextView1 != null && productImageView1 != null) {
                productNameTextView1.setText(product.getProduct());
                loadImage(product.getPhoto(), productImageView1);
            }
        }

        public void bindProduct2(ZaraItem product) {
            if (productNameTextView2 != null && productImageView2 != null) {
                productNameTextView2.setText(product.getProduct());
                loadImage(product.getPhoto(), productImageView2);
            }
        }

        private void loadImage(String imagePath, ImageView imageView) {
            int rotationAngle =0;
            if (!TextUtils.isEmpty(imagePath)) {
                Picasso.get()
                        .load(imagePath)
                        .rotate(rotationAngle)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                // Image loaded successfully
                            }

                            @Override
                            public void onError(Exception e) {
                                // Handle any errors while loading the image
                                Log.e("Image Loading", "Error loading image: " + e.getMessage());
                            }
                        });
            } else {
                // Handle the case where the imagePath is empty
                Log.e("Image Loading", "Empty image path!");
                // You can choose to display a placeholder image or take any other appropriate action
            }
    }

        public void hideSecondProduct() {
                // Hide the views for the second product in the pair
                productNameTextView2.setVisibility(View.GONE);
                productImageView2.setVisibility(View.GONE);
        }
        }


        public interface OnItemClickListener {
        void onItemClick(int position);

            void onItemClick(ZaraItem clickedItem);
        }
    public interface OnZaraItemClickListener {
        void onItemClick(ZaraItem item);
    }

    public void setOnItemClickListener(ZaraAdapter.OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
