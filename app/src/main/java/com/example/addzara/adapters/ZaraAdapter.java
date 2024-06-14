package com.example.addzara.adapters;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addzara.userInterface.DetailsFragment;
import com.example.addzara.activities.MainActivity;
import com.example.addzara.R;
import com.example.addzara.addData.ZaraItem;
import com.example.addzara.authentication.FirebaseServices;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ZaraAdapter extends RecyclerView.Adapter<ZaraAdapter.MyViewHolder> {
    private Context context;
    private  static  ArrayList<ZaraItem> zaList;
    private androidx.cardview.widget.CardView secondProductContainer; // Use CardView

    private ZaraAdapter.OnItemClickListener itemClickListener;
    private FirebaseServices fbs;


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
                args.putParcelable("products", zaList.get(position)); // or use Parcelable for better performance
                DetailsFragment cd = new DetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft= ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.Framelayoutmain4,cd);
                ft.commit();
            }

        } ;
    }

    public void setZaraItems(ArrayList<ZaraItem> zaraItems) {
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

        int firstProductIndex = position * 2;

        if (firstProductIndex < zaList.size()) {
            ZaraItem firstProduct = zaList.get(firstProductIndex);
            holder.bindProduct1(firstProduct);
            holder.itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(firstProductIndex);
                }
            });
        }

        if (firstProductIndex + 1 < zaList.size()) {
            ZaraItem secondProduct = zaList.get(firstProductIndex + 1);
            holder.bindProduct2(secondProduct);
            holder.secondProductContainer.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(firstProductIndex + 1);
                }
            });
        } else {
            holder.hideSecondProduct();
        }

        // Bind other views...

        // Set favorite icon based on the product's favorite status
        /* TODO: Yamen
        if (product.isFavorite()) {
            holder.favoriteIcon.setImageResource(R.drawable.bookmark__1_);
        } else {
            holder.favoriteIcon.setImageResource(R.drawable.bookmark__2_);
        } */

        // Set click listener for the favorite icon
     /*   holder.favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the favorite status of the product
                product.setFavorite(!product.isFavorite());
                notifyItemChanged(position);

                // If needed, notify the listener (Activity or Fragment) that the favorite status has changed
                if (onFavoriteClickListener != null) {
                    onFavoriteClickListener.onFavoriteClick(product);
                }
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil(zaList.size() / 2.0); // Round up to handle odd number of items

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageSwitcher favoriteIcon;
        public CardView secondProductContainer;
        private TextView productNameTextView1;
        private ImageView productImageView1;
        private TextView productNameTextView2;
        private ImageView productImageView2;
       //private Button favoriteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView1 = itemView.findViewById(R.id.tvProductName1);
            productImageView1 = itemView.findViewById(R.id.ivzaraItem1);
            productNameTextView2 = itemView.findViewById(R.id.tvProductName2);
            productImageView2 = itemView.findViewById(R.id.ivzaraItem2);
            secondProductContainer = itemView.findViewById(R.id.cardView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                       // ZaraItem clickedProduct = zaList.get(position);
                        itemClickListener.onItemClick(position);
                    }
                }
            });
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

        }

    public void setOnItemClickListener(ZaraAdapter.OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
    public static void setFilteredList(ArrayList<ZaraItem> filteredList){
        zaList = filteredList;
    }
}
