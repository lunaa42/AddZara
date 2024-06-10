package com.example.addzara.userInterface;


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
import android.widget.TextView;
import android.widget.Toast;

import com.example.addzara.R;
import com.example.addzara.addData.User;
import com.example.addzara.addData.ZaraItem;
import com.example.addzara.adapters.ZaraAdapter;
import com.example.addzara.authentication.FirebaseServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
    private RecyclerView recyclerView ;
    private FirebaseServices fbs;
    private ZaraAdapter myAdapter;
    private ArrayList<ZaraItem> products;
    private TextView userNameTextView;



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
        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        userNameTextView = view.findViewById(R.id.userNameTextView);
        return view;
    }



 public void onStart() {
        super.onStart();
        init();
    }

    private void init() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            StringBuilder userNameList = new StringBuilder();
                            for (DocumentSnapshot document : task.getResult()) {
                                // Assuming the user's name is stored under the "name" field
                                String userName = document.getString("name");
                                if (userName != null) {
                                    userNameList.append(userName).append("\n");
                                }
                            }
                            // Set user's name list to the TextView
                            userNameTextView.setText(userNameList.toString());
                        } else {
                          //  Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        fbs = FirebaseServices.getInstance();
        products = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        products = getCars();
        myAdapter = new ZaraAdapter(getActivity(), products);

        myAdapter.setOnItemClickListener(new ZaraAdapter.OnItemClickListener() {
            @Override
            public void onItemClick ( int position){
                // Handle item click here
                String selectedItem = products.get(position).getProduct();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putParcelable("product", products.get(position)); // or use Parcelable for better performance
                DetailsFragment cd = new DetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.Framelayoutmain4, cd);
                ft.commit();
            }
        });
    }

    private ArrayList<ZaraItem> getCars() {
        ArrayList<ZaraItem> products2 = new ArrayList<>();
        try {
            products2.clear();
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
                                            products2.add(document.toObject(ZaraItem.class));
                                    }
                                }
                                ZaraAdapter adapter = new ZaraAdapter(getActivity(), products2);
                                recyclerView.setAdapter(adapter);
                            } else {
                                // Handle error
                            }
                        }
                    });
        } catch (Exception e) {
            // Handle exception
        }
        return products2;
    }

}