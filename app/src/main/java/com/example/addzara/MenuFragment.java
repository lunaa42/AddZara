package com.example.addzara;

import android.app.AlertDialog;
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
import android.widget.Toast;

import com.example.addzara.DetailsFragment;
import com.example.addzara.FirebaseServices;
import com.example.addzara.ZaraAdapter;
import com.example.addzara.ZaraItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    private FirebaseServices fbs;
    private ArrayList<ZaraItem> product;
    private RecyclerView rvZaras;
    private ZaraAdapter adapter;
    private BottomNavigationView bottomNavigationView;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize FirebaseServices instance
        fbs = FirebaseServices.getInstance();
        // Rest of your code...
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        bottomNavigationView = view.findViewById(R.id.bottomnavmenu);
        rvZaras = view.findViewById(R.id.rvmenu);
        rvZaras.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize adapter with an empty list initially
        product = new ArrayList<>();
        adapter = new ZaraAdapter(getContext(), product);

        // Set the adapter to the RecyclerView
        rvZaras.setAdapter(adapter);

        // Load data from Firestore or wherever you are getting it
        loadDataFromFirestore();
        rvZaras.setHasFixedSize(true);
        if(product.isEmpty()){   Toast.makeText(getActivity(), "its empty ", Toast.LENGTH_SHORT).show();}
        else {
            Toast.makeText(getActivity(), "its not empty ", Toast.LENGTH_SHORT).show();
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                if (item.getItemId() == R.id.homenav2) {
                    selectedFragment = new HomeFragment();
                } else if (item.getItemId() == R.id.menunav2) {
                    selectedFragment = new AddZaraFragment();
                } else if (item.getItemId() == R.id.favnav2) {
                    selectedFragment = new FavFragment();
                }

                if (selectedFragment != null) {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Framelayoutmain4, selectedFragment)
                            .addToBackStack(null)
                            .commit();
                }

                return true;
            }
        });

        return view;
    }

    private void loadDataFromFirestore() {
        // Assume you have a Firestore collection named "products"
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productsRef = db.collection("products");

        // You can add query conditions or limit here if needed
        productsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                // Parse data from the document and create a Product object
                ZaraItem productItem = documentSnapshot.toObject(ZaraItem.class);

                // Add the product to the ArrayList
                product.add(productItem);
            }

            // Notify the adapter that the data set has changed
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Handle the failure to fetch data
            Toast.makeText(getActivity(), "Failed to load data", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        fbs = FirebaseServices.getInstance();
        product = new ArrayList<>();
       // product = getProduct();
        rvZaras = getView().findViewById(R.id.rvmenu);
        adapter = new ZaraAdapter(getActivity(), product);
        rvZaras.setAdapter(adapter);
        rvZaras.setHasFixedSize(true);
        rvZaras.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setZaraItems(product);

        fbs.getFire().collection("tiles").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dataSnapshot : queryDocumentSnapshots.getDocuments()) {
                    ZaraItem za = dataSnapshot.toObject(ZaraItem.class);

                    product.add(za);
                }

                adapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                Log.e("MENUFRAGMENT", e.getMessage());
            }
        });
       /* adapter.setOnItemClickListener(new ZaraAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String selectedItem = product.get(position).getProduct();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putParcelable("product", product.get(position));
                DetailsFragment de = new DetailsFragment();
                de.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.Framelayoutmain4, de);
                ft.commit();
            }
        });*/
    }

    /*private void applyFilter(String query) {
        if (query.trim().isEmpty()) {
            adapter = new ZaraAdapter(getContext(), product);
            rvZaras.setAdapter(adapter);
            return;
        }

        filteredList.clear();
        for (ZaraItem prod : product) {
            if (prod.getProduct().toLowerCase().contains(query.toLowerCase()) ||
                    prod.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                    prod.getColour().toLowerCase().contains(query.toLowerCase()) ||
                    prod.getSize().toLowerCase().contains(query.toLowerCase()) ||
                    prod.getPrice().toLowerCase().contains(query.toLowerCase()) ||
                    prod.getPhoto().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(prod);
            }
        }

        if (filteredList.size() == 0) {
            showNoDataDialogue();
            return;
        }

        adapter = new ZaraAdapter(getContext(), filteredList);
        rvZaras.setAdapter(adapter);

        adapter.setOnItemClickListener(new ZaraAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click for filtered list
            }
        });
    }
*/
    private void showNoDataDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("No Results");
        builder.setMessage("Try again!");
        builder.show();
    }

    public ArrayList<ZaraItem> getProduct() {
        ArrayList<ZaraItem> products = new ArrayList<>();

        try {
            products.clear();
            fbs.getFire().collection("product2")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    products.add(document.toObject(ZaraItem.class));
                                }
                                adapter.setZaraItems(products);
                            } else {
                                Log.e("MenuFragment", "Error getting documents.", task.getException());
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e("MenuFragment", "getProduct(): " + e.getMessage());
        }

        return products;
    }
}
