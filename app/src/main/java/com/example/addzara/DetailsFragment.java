package com.example.addzara;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    private static final int PERMISSION_SEND_SMS = 1;
    private static final int REQUEST_CALL_PERMISSION = 2;
    private FirebaseServices fbs;
    private TextView tvproduct,tvsize,tvcolour,tvdescription,tvprice;
    private ArrayList<ZaraItem> favoriteProducts = new ArrayList<>();
    private ImageView ivproductPhoto,ivfav;
    private ImageView GoBack;
    private ZaraItem myproduct;
    private Button btnBuy;
    private boolean isFavorite = false; // Initially, the product is not favorited


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(ZaraItem item) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("products", item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Initialize views here
        tvproduct = view.findViewById(R.id.tvproduct2Deta);
        tvsize = view.findViewById(R.id.tvsize2Deta);
        tvcolour = view.findViewById(R.id.tvcolourDeta);
        tvprice = view.findViewById(R.id.tvprice2Deta);
        tvdescription = view.findViewById(R.id.tvdescri2Deta);
        GoBack = view.findViewById(R.id.gobackDetails);
        ivfav = view.findViewById(R.id.favorite_button);
        ivproductPhoto = view.findViewById(R.id.ivProductdeta);
        btnBuy = view.findViewById(R.id.btnBuydetail);

       /* if (myproduct.isFavorite()) {
            ivfav.setImageResource(R.drawable.bookmark__1_);
        } else {
            ivfav.setImageResource(R.drawable.bookmark__2_);
        }
        initializeSelectedItem();

        // Assuming you have a favorite button in your layout with the ID "favoriteButton"
        Button favoriteButton = getView().findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the favorite status of the selected item
                myproduct.setFavorite(!myproduct.isFavorite());

                // Notify the Menu Fragment about the change
                ((MenuFragment) getParentFragment()).updateProductFavoriteStatus(myproduct);

                // Update the UI of the favorite button based on the new status
                updateFavoriteButtonUI();
            }
        });*/
        // Call init method to set data
        init();

        return view;    }
    @Override
    public void onStart() {
        super.onStart();
        init();
    }
    public void init()
    {
        // Inside the method where the favorite button click is handled


        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMenu();
            }
        });


        Bundle args = getArguments();
        if (args != null) {
            myproduct = args.getParcelable("products");
            if (myproduct != null) {
                Log.d("DetailsFragment", "Product details: " + myproduct.getProduct());

                //String data = myObject.getData();
                // Now you can use 'data' as needed in FragmentB
                tvproduct.setText(myproduct.getProduct());
                tvsize.setText(myproduct.getSize());
                tvcolour.setText(myproduct.getColour());
                tvprice.setText(myproduct.getPrice() + " ₪");
                tvdescription.setText(myproduct.getDescription());
                if (myproduct.getPhoto() == null || myproduct.getPhoto().isEmpty())
                {
                    Picasso.get().load(R.drawable.clothinhw1_removebg_preview).into(ivproductPhoto);
                }
                else {
                    Picasso.get().load(myproduct.getPhoto()).into(ivproductPhoto);
                }
            }
        }
      //  btnBuy = getView().findViewById(R.id.btnBuydetail);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*gotobuy();*/            }
        });

    }

    private void gotoMenu() {
        FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayoutmain4,new MenuFragment());
        ft.commit();
    }


    private boolean toggleFavoriteStatus() {
            // Toggle the favorite status of the product
            isFavorite = !isFavorite;
        if (isFavorite) {
            favoriteProducts.add(myproduct); // Assuming 'product' is the current product being viewed
        } else {
            // If the product is unfavorited, remove it from the list of favorite products
            favoriteProducts.remove(myproduct); // Assuming 'product' is the current product being viewed
        }
            return isFavorite;

        }
    private void initializeSelectedItem() {
        // This method should initialize the selectedItem based on the product being viewed
        // You should replace the code below with your actual implementation
        // For example, if you pass the product details via arguments, retrieve them here
        Bundle args = getArguments();
        if (args != null) {
            myproduct = args.getParcelable("product"); // Assuming "product" is the key used to pass the product details
        }
    }

    private void updateFavoriteButtonUI() {
        // Update the UI of the favorite button based on the new status
        Button favoriteButton = getView().findViewById(R.id.favorite_button);
        if (myproduct.isFavorite()) {
            favoriteButton.setText("Remove from Favorites");
        } else {
            favoriteButton.setText("Add to Favorites");
        }
    }

}