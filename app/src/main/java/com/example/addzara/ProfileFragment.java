package com.example.addzara;

import static com.example.addzara.R.id.bottomnavProfile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvFirstName, tvLastName, tvEmail, tvPhone,tvSignout;
    private String stFirstName, stLastName, stEmail, stPhone;
    private static final int GALLERY_REQUEST_CODE = 134;
    private ImageView imgprofile;
    private FirebaseServices fbs;
    private FirebaseFirestore db;
    private FirebaseAuth mauth;
    private BottomNavigationView bottomNavigationView;

    private boolean flagAlreadyFilled = false;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize UI elements
        tvFirstName = view.findViewById(R.id.tvfirstnameprofile);
        tvLastName = view.findViewById(R.id.ConstrainLayout);
        tvEmail = view.findViewById(R.id.tvemailProfile);
        tvPhone = view.findViewById(R.id.tvphoneprofile);
        imgprofile = view.findViewById(R.id.imgprofileprofile);
        tvSignout = view.findViewById(R.id.tvsignoutprofile);
        bottomNavigationView = view.findViewById(R.id.bottomnavProfile);

        // Fill user details
        fillUserDetails();
    }

    private void fillUserDetails() {
        // Check if user details are already filled
        if (flagAlreadyFilled) {
            return;
        }

        // Retrieve current user information
        User current = fbs.getCurrentUser();
        if (current != null) {
            tvFirstName.setText(current.getFirstName());
            tvLastName.setText(current.getLastName());
            tvEmail.setText(current.getUsername());
            tvPhone.setText(current.getPhone());

        }
    }

    @Override
       public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_profile, container, false);

       // Initialize BottomNavigationView
       BottomNavigationView bottomNavigationView = view.findViewById(bottomnavProfile);

       // Set up item selection listener
       bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               Fragment selectedFragment = null;
               if (item.getItemId() == R.id.homenav2) {
                   selectedFragment = new HomeFragment();
               } else if (item.getItemId() == R.id.menunav2) {
                   selectedFragment = new MenuFragment();
               } else if (item.getItemId() == R.id.favnav2) {
                   selectedFragment = new FavFragment();
               }

               if (selectedFragment != null) {
                   // Replace the current fragment with the selected one
                   requireActivity().getSupportFragmentManager().beginTransaction()
                           .replace(R.id.Framelayoutmain4, selectedFragment)
                           .addToBackStack(null) // Optional: Add to back stack if you want to navigate back
                           .commit();
               }

               return true;
           }
       });

       return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init()
    {
        fbs = FirebaseServices.getInstance();
        tvFirstName=getView().findViewById(R.id.tvfirstnameprofile);
        tvLastName=getView().findViewById(R.id.ConstrainLayout);
        tvEmail=getView().findViewById(R.id.tvemailProfile);
        tvPhone=getView().findViewById(R.id.tvphoneprofile);
        bottomNavigationView = getView().findViewById(R.id.bottomnavProfile);
        imgprofile=getView().findViewById(R.id.imgprofileprofile);
        tvSignout= getView().findViewById(R.id.tvsignoutprofile);
        db = FirebaseFirestore.getInstance();
        mauth = FirebaseAuth.getInstance();
        String firstname = tvFirstName.getText().toString();
        String lastname = tvLastName.getText().toString();
        String email = tvEmail.getText().toString();
        String phone = tvPhone.getText().toString();
        fillUserDetails();

        tvSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Data validation
                mauth.signOut();
                FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.Framelayoutmain4,new HomeFragment());
                ft.commit();
                }
            });
    }

}