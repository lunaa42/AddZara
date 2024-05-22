package com.example.addzara.userInterface;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.addzara.R;
import com.example.addzara.authentication.FirebaseServices;
import com.example.addzara.authentication.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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
        db = FirebaseFirestore.getInstance();
        mauth = FirebaseAuth.getInstance();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Ensure FirebaseServices object is initialized
        fbs = FirebaseServices.getInstance();
        // Call the method after ensuring fbs is not null

        // Check if user is logged in
        FirebaseUser currentUser = mauth.getCurrentUser();
        if (currentUser != null) {
            // User is logged in, display profile details
            fillUserDetails(currentUser);
        } else {
            // User is not logged in, navigate to login fragment
            navigateToLoginFragment();
        }
    }

    private void fillUserDetails(FirebaseUser user) {
        String currentUserEmail = getCurrentUserEmail();
        if (user != null) {
           // Assuming you have a method to get current user's email
            if (currentUserEmail != null) {
                db.collection("users")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String userEmail = document.getString("email");
                                    if (userEmail != null && userEmail.equals(currentUserEmail)) {
                                        // Current user found in database
                                        //tvFirstName.setText(ge);
                                       // tvLastName.setText(lastName);
                                        tvEmail.setText(user.getEmail());
                                        tvPhone.setText(user.getPhoneNumber());
                                        // Do something
                                    }
                                }
                            } else {
                                // Handle failure
                            }
                        });
            }
        }
            // Assuming you have stored user's first and last names in Firestore

            /* TODO:  get all users from firebase
               - loop all users and make sure: user.getEmail().equals(fbs.getAuth.getCurrentUser().getEmail())

                tvFirstName.setText(user.getFirstName());
                Picasso...
             */

           /* FirebaseFirestore.getInstance().collection("users")
                    .document(user.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String firstName = documentSnapshot.getString("firstName");
                                String lastName = documentSnapshot.getString("lastName");
                                String email = documentSnapshot.getString("email");
                                String phone = documentSnapshot.getString("phone");


                                // Populate TextViews with retrieved first and last names
                                tvFirstName.setText(firstName);
                                tvLastName.setText(lastName);
                                tvEmail.setText(email);
                                tvPhone.setText(phone);
                            }
                        }
                    });*/
        }

    private String getCurrentUserEmail() {
        if (mauth.getCurrentUser() != null) {
            return mauth.getCurrentUser().getEmail();
        }
        return null;
    }

    @Override
       public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        tvFirstName = view.findViewById(R.id.tvfirstnameprofile);
        tvLastName = view.findViewById(R.id.tvlastnameProfile);
        tvEmail = view.findViewById(R.id.tvemailProfile);
        tvPhone = view.findViewById(R.id.tvphoneprofile);
        imgprofile = view.findViewById(R.id.imgprofileprofile);
        tvSignout = view.findViewById(R.id.tvsignoutprofile);

        // Set sign out click listener
        tvSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out user
                mauth.signOut();
                // Navigate to HomeFragment after sign out
                navigateToHomeFragment();
            }
        });

        return view;
    }

    private void navigateToHomeFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayoutmain4, new HomeFragment());
        ft.commit();
    }

    private void navigateToLoginFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayoutmain4, new LoginFragment());
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
     //   init();
    }

    private void init()
    {
        fbs = FirebaseServices.getInstance();
        tvFirstName=getView().findViewById(R.id.tvfirstnameprofile);
        tvLastName=getView().findViewById(R.id.tvlastnameProfile);
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