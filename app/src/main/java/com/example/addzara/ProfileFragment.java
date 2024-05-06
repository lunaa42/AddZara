package com.example.addzara;

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

       // Set up item selection listener

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
        imgprofile=getView().findViewById(R.id.imgprofileprofile);
        tvSignout= getView().findViewById(R.id.tvsignoutprofile);
        db = FirebaseFirestore.getInstance();
        mauth = FirebaseAuth.getInstance();

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