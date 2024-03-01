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
                boolean itemSelected = false;
                if (item.getItemId() == R.id.homenav2) {
                    selectedFragment = new HomeFragment();
                    itemSelected = true;
                } else if (item.getItemId() == R.id.menunav2) {
                    selectedFragment = new MenuFragment();
                    itemSelected = true;
                } else if (item.getItemId() == R.id.profilenav2) {
                    selectedFragment = new LoginFragment();
                    itemSelected = true;
                } else if (item.getItemId() == R.id.favnav2) {
                    selectedFragment = new FavFragment();
                    itemSelected = true;
                }

                if (selectedFragment != null) {
                    // Replace the current fragment with the selected one
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Framelayoutmain4, selectedFragment)
                            .addToBackStack(null) // Optional: Add to back stack if you want to navigate back
                            .commit();
                }

                // Check if the item was selected
                if (itemSelected) {
                    // Set the selected item in the bottom navigation bar
                    item.setChecked(true);
                }

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
