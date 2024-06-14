package com.example.addzara.userInterface;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.addzara.R;
import com.example.addzara.adapters.ZaraAdapter;
import com.example.addzara.addData.User;
import com.example.addzara.addData.ZaraItem;
import com.example.addzara.authentication.FirebaseServices;
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
    private TextView tvproduct,tvcolour,tvdescription,tvprice;
    private ImageView ivproductPhoto;
    private ImageView GoBack;
    private ZaraItem myproduct;
    Spinner SizeSpinner;
    private ArrayList<ZaraItem> product;
    private ZaraAdapter adapter;

    private ImageView favoriteImageView ;
    private boolean isFavorite = false;
    private static final String[] CATEGORIES = {"choose your size","XSMALL","SMALL", "Medium", "LARGE","XLARGE"};
    private Button btnBuy;


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
        tvproduct = view.findViewById(R.id.tvproduct2Deta);
        favoriteImageView = view.findViewById(R.id.favorite_button);
        fbs = FirebaseServices.getInstance();
        SizeSpinner = view.findViewById(R.id.sizeSpinner);
        tvcolour = view.findViewById(R.id.tvcolourDeta);
        tvprice = view.findViewById(R.id.tvprice2Deta);
        tvdescription = view.findViewById(R.id.tvdescri2Deta);
        GoBack = view.findViewById(R.id.gobackDetails);
        ivproductPhoto = view.findViewById(R.id.ivProductdeta);
        btnBuy = view.findViewById(R.id.btnBuydetail);
        init();
        return view;    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the ZaraAdapter here
        adapter = new ZaraAdapter(requireContext(), new ArrayList<ZaraItem>());

        Spinner categorySpinner = view.findViewById(R.id.sizeSpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, CATEGORIES);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCategory = CATEGORIES[position];
                Log.d("DetailsFragment", "Selected category: " + selectedCategory);

                ArrayList<ZaraItem> filteredList = new ArrayList<>();
                // Inside the onItemSelected method of AdapterView.OnItemSelectedListener
                // For now, we are just setting an empty list to adapter
                // You may need to modify this to filter products based on selected category
                adapter.setZaraItems(filteredList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        checkInitialFavoriteStatus();

        init();
    }

    private void checkInitialFavoriteStatus() {
        User currentUser = fbs.getCurrentUser();
        if(currentUser!=null){
            ArrayList<String> favorites = currentUser.getFavorites();

            if(favorites.contains(myproduct.getProduct())){
                favoriteImageView.setImageResource(R.drawable.bookmark__2_);
                isFavorite=true;
            }
        }
    }

    public void init()
    {


        favoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on favorite button
                toggleFavorite();
            }

           
        });
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMenu();
            }
        });


        Bundle args = getArguments();
        if (args != null) {
            myproduct = args.getParcelable("product");
            if (myproduct != null) {
                Log.d("DetailsFragment", "Product details: " + myproduct.getProduct());

                //String data = myObject.getData();
                // Now you can use 'data' as needed in FragmentB
                tvproduct.setText(myproduct.getProduct());
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
                BottomSheetBinding BottomSheetBinding = new BottomSheetBinding();
                BottomSheetBinding.show(getParentFragmentManager(), BottomSheetBinding.getTag());          }
        });

    }

    private void toggleFavorite() {
        
            // Check if the product is already in the favorite list
            if (isFavorite) {
                // Remove the product from the favorite list
                removeFromFavorites();
            } else {
                // Add the product to the favorite list
                addToFavorites();
            }

            // Toggle the favorite state
            isFavorite = !isFavorite;

            // Update the favorite button icon
         //   updateFavoriteButtonIcon();
        }

    private void addToFavorites() {
        User currentUser = fbs.getCurrentUser();

        // Check if the user is logged in
        if (currentUser != null) {
            // Get the list of favorites from the current user
            ArrayList<String> favorites = currentUser.getFavorites();

            // Check if the product is not already in the favorites list
            if (!favorites.contains(myproduct.getProduct())) {
                // Add the product to the favorites list
                favorites.add(myproduct.getProduct());

                // Update the favorites data in Firestore
                fbs.updateUserFavorites(currentUser.getFirstName(), favorites);

                // Notify the user that the product has been added to favorites
                Toast.makeText(getActivity(), "Added to favorites", Toast.LENGTH_SHORT).show();
                favoriteImageView.setImageResource(R.drawable.bookmark__2_);
                isFavorite = true;
            } else {
                // Notify the user that the product is already in favorites
                Toast.makeText(getActivity(), "Product already in favorites", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Notify the user to log in first
            Toast.makeText(getActivity(), "Please log in to add to favorites", Toast.LENGTH_SHORT).show();
        }
    }

  /*  private void updateFavoriteButtonIcon() {
        if (isFavorite) {
            favoriteImageView.setImageResource(R.drawable.bookmark__2_);
        } else {
            favoriteImageView.setImageResource(R.drawable.bookmark__1_);
        }
    }*/

    private void removeFromFavorites() { // Get the current user
        User currentUser = fbs.getCurrentUser();

        // Check if the user is logged in
        if (currentUser != null) {
            // Get the list of favorites from the current user
            ArrayList<String> favorites = currentUser.getFavorites();

            // Check if the product is in the favorites list
            if (favorites.contains(myproduct.getProduct())) {
                // Remove the product from the favorites list
                favorites.remove(myproduct.getProduct());

                // Update the favorites data in Firestore
                fbs.updateUserFavorites(currentUser.getFirstName(), favorites);
                favoriteImageView.setImageResource(R.drawable.bookmark__1_);
                // Notify the user that the product has been removed from favorites
                Toast.makeText(getActivity(), "Removed from favorites", Toast.LENGTH_SHORT).show();
            } else {
                // Notify the user that the product is not in favorites
                Toast.makeText(getActivity(), "Product not in favorites", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Notify the user to log in first
            Toast.makeText(getActivity(), "Please log in to remove from favorites", Toast.LENGTH_SHORT).show();
        }
    }


    private void gotoMenu() {
        FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayoutmain4,new MenuFragment());
        ft.commit();
    }

}
