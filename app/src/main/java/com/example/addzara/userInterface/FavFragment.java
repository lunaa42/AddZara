package com.example.addzara.userInterface;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addzara.R;
import com.example.addzara.activities.MainActivity;
import com.example.addzara.addData.User;
import com.example.addzara.addData.ZaraItem;
import com.example.addzara.adapters.ZaraAdapter;
import com.example.addzara.authentication.FirebaseServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FavFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private FirebaseServices fbs;
    private ZaraAdapter myAdapter;
    private TextView current;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ZaraItem> products;
    private TextView userNameTextView;

    public FavFragment() {
        // Required empty public constructor
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        userNameTextView = view.findViewById(R.id.userNameTextView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        current = getView().findViewById(R.id.usercurrentname);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String userID = currentUser.getUid();
        fbs = FirebaseServices.getInstance();
        products = new ArrayList<>();
        loadFavorites();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String firstName = document.getString("firstName");
                                if (firstName != null) {
                                    current.setText(firstName);
                                }
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        fbs = FirebaseServices.getInstance();
        products = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getFavoriteProducts();
    }

    private void loadFavorites() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            FirebaseFirestore.getInstance().collection("users")
                    .document(currentUser.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        ArrayList<String> favoriteIds = (ArrayList<String>) documentSnapshot.get("favorites");
                        if (favoriteIds != null) {
                            fetchFavoriteProducts(favoriteIds);
                        }
                    })
                    .addOnFailureListener(e -> Log.e("FavFragment", "Error loading favorites", e));
        }
    }

    private void fetchFavoriteProducts(ArrayList<String> favoriteIds) {
        FirebaseFirestore.getInstance().collection("products")
                .whereIn("productId", favoriteIds)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (DocumentSnapshot document : task.getResult()) {
                            ZaraItem item = document.toObject(ZaraItem.class);
                            products.add(item);
                        }
                        setupRecyclerView();
                    } else {
                        Log.e("FavFragment", "Error fetching favorite products", task.getException());
                    }
                });
    }

    private void setupRecyclerView() {
        myAdapter = new ZaraAdapter(getActivity(), products);
        myAdapter.setOnItemClickListener(position -> {
            ZaraItem selectedItem = products.get(position);
            Toast.makeText(getActivity(), "Clicked: " + selectedItem.getProduct(), Toast.LENGTH_SHORT).show();
            Bundle args = new Bundle();
            args.putParcelable("product", selectedItem);
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(args);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.Framelayoutmain4, detailsFragment);
            ft.commit();
        });
        recyclerView.setAdapter(myAdapter);
    }


    private void getFavoriteProducts() {
        fbs.getFire().collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            products.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User u = fbs.getCurrentUser();
                                if (u != null) {
                                    ZaraItem product = document.toObject(ZaraItem.class);
                                    if (u.getFavorites().contains(product.getProduct())) {
                                        products.add(product);
                                    }
                                }
                            }
                            myAdapter = new ZaraAdapter(getActivity(), products);
                            recyclerView.setAdapter(myAdapter);

                            myAdapter.setOnItemClickListener(new ZaraAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    ZaraItem selectedItem = products.get(position);
                                    Toast.makeText(getActivity(), "Clicked: " + selectedItem.getProduct(), Toast.LENGTH_SHORT).show();
                                    Bundle args = new Bundle();
                                    args.putParcelable("product", selectedItem);
                                    DetailsFragment detailsFragment = new DetailsFragment();
                                    detailsFragment.setArguments(args);
                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.Framelayoutmain4, detailsFragment);
                                    ft.addToBackStack(null);
                                    ft.commit();
                                }
                            });
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        User u = ((MainActivity) getActivity()).getUserDataObject();
        if (u != null) {
            fbs.updateUser(u);
        }
    }
}
