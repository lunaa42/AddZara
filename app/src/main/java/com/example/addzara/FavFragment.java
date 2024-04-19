package com.example.addzara;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<ZaraItem> favoriteProducts;
    private FirebaseServices fbs;
    private RecyclerView recyclerView;
    private ZaraAdapter adapter;
    private ImageView cancelimg;
    private Button Fav;
    private TextView name;


    public FavFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavFragment newInstance(String param1, String param2) {
        FavFragment fragment = new FavFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

   /* private String getUserDisplayName() {
        return "User's Name"; // Example: returning a hardcoded name for demonstration

    }*/

   /* public void onStart() {
        super.onStart();
        init();
    }

    private void init() {

        recyclerView = getView().findViewById(R.id.favoritesRecyclerView);
        fbs = FirebaseServices.getInstance();
        favoriteProducts = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        favoriteProducts = getProducts();
        adapter = new ZaraAdapter(getActivity(), favoriteProducts);
        name = getView().findViewById(R.id.userNameTextView);

        // Assuming you have a method to retrieve the user's name
        String userName = getUserNameFromAuthentication(); // Replace this with your logic to retrieve the user's name

        // Set the user's name to the TextView
        name.setText(userName);
        adapter.setOnItemClickListener(new ZaraAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                String selectedItem = favoriteProducts.get(position).getProduct();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putParcelable("product", favoriteProducts.get(position)); // or use Parcelable for better performance
                DetailsFragment cd = new DetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.Framelayoutmain4,cd);
                ft.commit();
            }
        });
/*
        fbs.getFire().collection("cars").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){
                    Car car= dataSnapshot.toObject(Car.class);
                    list.add(car);
                }


                myAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    


    }
/*
    private String getUserNameFromAuthentication() {
        // Assuming you're using Firebase Authentication
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Check if the user is signed in
        if (user != null) {
            // Get the user's display name
            String displayName = user.getDisplayName();

            // Check if the display name is not null or empty
            if (displayName != null && !displayName.isEmpty()) {
                return displayName;
            } else {
                // If the display name is null or empty, return a default name
                return "User";
            }
        } else {
            // If the user is not signed in, return a default name
            return "User";
        }*/
    }
/*
    public ArrayList<ZaraItem> getProducts() {


        ArrayList<ZaraItem> products = new ArrayList<>();

        try {
            products.clear();
            fbs.getFire().collection("products")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User u = fbs.getCurrentUser();
                                    if (u != null) {
                                        ZaraItem product = document.toObject(ZaraItem.class);
                                        if (u.getFavorites().contains(product.getProduct()))
                                            products.add(document.toObject(ZaraItem.class));
                                    }
                                }

                                ZaraAdapter adapter = new ZaraAdapter(getActivity(), products);
                                recyclerView.setAdapter(adapter);
                                //addUserToCompany(companies, user);
                            } else {
                                //Log.e("AllRestActivity: readData()", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            Log.e("getCompaniesMap(): ", e.getMessage());
        }

        return products;
    }*/

 /*   private void loadFavoriteProducts() {
        favoriteProducts.clear();
        for (ZaraItem product : ) {
            if (product.isFavorite()) {
                favoriteProducts.add(product);
            }
        }
        adapter.notifyDataSetChanged();
    }
}*/