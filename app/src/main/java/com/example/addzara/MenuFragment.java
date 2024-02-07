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
import androidx.annotation.Nullable;
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

public class MenuFragment extends Fragment {
    private FirebaseServices fbs;
    private ArrayList<ZaraItem> product;
    private RecyclerView rvZaras;
    private ZaraAdapter adapter;
    private BottomNavigationView bottomNavigationView;

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

        fbs = FirebaseServices.getInstance();
        product = new ArrayList<>();
        adapter = new ZaraAdapter(getContext(), product);
        rvZaras.setAdapter(adapter);
        rvZaras.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load data from Firestore
        loadDataFromFirestore();

        bottomNavigationView = view.findViewById(R.id.bottomnavmenu);
        // Set BottomNavigationView listener
        // Implement item selection logic here
    }

    private void loadDataFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productsRef = db.collection("products");

        productsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                ZaraItem productItem = documentSnapshot.toObject(ZaraItem.class);
                product.add(productItem);
            }
            adapter.notifyDataSetChanged(); // Notify adapter of data changes
        }).addOnFailureListener(e -> {
            Toast.makeText(getActivity(), "Failed to load data", Toast.LENGTH_SHORT).show();
            Log.e("MenuFragment", "Error: " + e.getMessage());
        });
    }
}
