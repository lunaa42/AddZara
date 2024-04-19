package com.example.addzara;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
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
import com.example.addzara.ZaraAdapter;


import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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
    private SearchView searchView2;
    private static final String[] CATEGORIES = {"WOMEN", "MEN", "BEAUTY"};
    Spinner categorySpinner;






    // Constructor and newInstance() method remain the same

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        rvZaras = view.findViewById(R.id.rvmenu);
        categorySpinner = view.findViewById(R.id.categorySpinner);
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
        searchView2 = getView().findViewById(R.id.searchView);
        searchView2.clearFocus();
        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
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
                int row = position ; // Calculate the row index
                int column = position % 2; // Calculate the column index

                int productIndex = row * 2 + column;
                ZaraItem selectedItem = product.get(productIndex);
                // Toggle the favorite status of the selected item
                selectedItem.setFavorite(!selectedItem.isFavorite());
                // Notify the adapter to update the UI
                adapter.notifyItemChanged(position);
                Log.d("MenuFragment", "Selected item: " + selectedItem.toString());

                Toast.makeText(getActivity(), "Clicked: " + selectedItem.getProduct(), Toast.LENGTH_SHORT).show();

                Bundle args = new Bundle();
                args.putParcelable("products", selectedItem);
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(args);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.Framelayoutmain4, detailsFragment);
                ft.commit();

            }

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

        // Inside the onViewCreated method

        Spinner categorySpinner = getView().findViewById(R.id.categorySpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, CATEGORIES);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCategory = CATEGORIES[position];
                Log.d("MenuFragment", "Selected category: " + selectedCategory);

                ArrayList<ZaraItem> filteredList = new ArrayList<>();
                // Inside the onItemSelected method of AdapterView.OnItemSelectedListener
                for (ZaraItem item : product) {
                    // Check if item is not null before accessing its properties
                    if (item != null && item.getCategory() != null && item.getCategory().equals(selectedCategory)) {
                        filteredList.add(item);
                    }
                }


                // Use the existing instance of ZaraAdapter and call setZaraItems
                adapter.setZaraItems(filteredList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });




    }

    public void filterList(String newText) {
        ArrayList<ZaraItem> filteredList = new ArrayList<>();
        for (ZaraItem item : product){
            if(item.getProduct().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(getActivity(), "NO DATA FOUND: " , Toast.LENGTH_SHORT).show();
        } else {
            adapter.setZaraItems(filteredList);
        }
    }


    public void updateProductFavoriteStatus(ZaraItem myproduct) {
        int position = product.indexOf(myproduct);
        if (position != -1) {
            // Update the item in the productList
            product.set(position, myproduct);
            // Notify the adapter to update the UI
            adapter.notifyItemChanged(position);
    }
}
}
