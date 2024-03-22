package com.example.addzara;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MenuFragment extends Fragment {
    private FirebaseServices fbs;
    private ArrayList<ZaraItem> product;
    private RecyclerView rvZaras;
    private ZaraAdapter adapter;
    private BottomNavigationView bottomNavigationView;
    private ImageView addP;


    // Constructor and newInstance() method remain the same

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        rvZaras = view.findViewById(R.id.rvmenu);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void loadDataFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productsRef = db.collection("products");

        productsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()) {
                ZaraItem productItem = dataSnapshot.toObject(ZaraItem.class);
                product.add(productItem);

            }
            adapter.notifyItemInserted(product.size() - 1); // Notify adapter of the inserted item

        }).addOnFailureListener(e -> {
            Toast.makeText(getActivity(), "Failed to load data", Toast.LENGTH_SHORT).show();
            Log.e("MenuFragment", "Error: " + e.getMessage());
        });
    }
    public void onStart() {
        super.onStart();
        fbs = FirebaseServices.getInstance();
        addP = getView().findViewById(R.id.addProductMenu);
        product = new ArrayList<>();
        rvZaras.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ZaraAdapter(getContext(), product);
        rvZaras.setAdapter(adapter);
        // Assuming you have retrieved product data from Firestore and stored it in a List<ZaraItem> productList
        adapter.setZaraItems(product);
        adapter.setOnItemClickListener(new ZaraAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                String selectedItem = product.get(position).getProduct();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putParcelable("product", product.get(position)); // or use Parcelable for better performance
                DetailsFragment cd = new DetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.Framelayoutmain4,cd);
                ft.commit();
            }

         /*   @Override
            public void onItemClick(ZaraItem clickedItem) {


            }*/
        });

        // Load data from Firestore
        loadDataFromFirestore();

        bottomNavigationView = getView().findViewById(R.id.bottomnavmenu);
        // Set BottomNavigationView listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                if (selectedFragment != null) {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Framelayoutmain4, selectedFragment)
                            .addToBackStack(null)
                            .commit();

                    // Select the corresponding item in the bottom navigation
                    switch (selectedFragment.getClass().getSimpleName()) {
                        case "HomeFragment":
                            bottomNavigationView.setSelectedItemId(R.id.homenav2);
                            break;
                        case "MenuFragment":
                            bottomNavigationView.setSelectedItemId(R.id.menunav2);
                            break;
                        case "LoginFragment":
                            bottomNavigationView.setSelectedItemId(R.id.profilenav2);
                            break;
                        case "FavFragment":
                            bottomNavigationView.setSelectedItemId(R.id.favnav2);
                            break;
                    }
                }
                // Check if the item was selected

                return true;
            }
        });
        addP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.Framelayoutmain4,new AddZaraFragment());
                ft.commit();
            }
        });
    }

}
