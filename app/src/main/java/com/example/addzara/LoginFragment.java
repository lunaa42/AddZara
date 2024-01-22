package com.example.addzara;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

        private EditText etUsername,etPassword;
        private Button btnLogin;
        private FirebaseServices fbs;
        private TextView tvSignupLink;
        private TextView tvforgotpassword;
        private BottomNavigationView bottomNavigationView;

        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;

        public LoginFragment() {
            // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static LoginFragment newInstance(String param1, String param2) {
            LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login2, container, false);

        // Initialize BottomNavigationView
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomnavlogin);

        // Set up item selection listener
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
            // connecting components
            fbs =  FirebaseServices.getInstance();
            etUsername = getView().findViewById(R.id.etUsernameLogin);
            etPassword = getView().findViewById(R.id.etPasswordLogin);
            btnLogin = getView().findViewById(R.id.btnloginLogin);
            tvSignupLink = getView().findViewById(R.id.tvSignupLink);
            bottomNavigationView = getView().findViewById(R.id.bottomnavlogin);
            tvforgotpassword = getView().findViewById(R.id.tvforgotpasswordLogin);
            tvSignupLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoSignupFragment();

                }
            });
            tvforgotpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoforgotPassFragment();
                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Data validation
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();
                    if(username.trim().isEmpty() && password.trim().isEmpty()){
                        Toast.makeText(getActivity(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Login procedure
                    Task<AuthResult> authResultTask = fbs.getAuth().signInWithEmailAndPassword(username, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "You have successfully logged in!", Toast.LENGTH_SHORT).show();
                                gotoAddzara();

                            } else
                            {
                                Toast.makeText(getActivity(), "Failed to login! Check user or password..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

        }
        private void gotoSignupFragment() {
            FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.Framelayoutmain4,new SignupFragment());
            ft.commit();
        }
         private void gotoforgotPassFragment() {
        FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayoutmain4,new ForgotPasswordFragment());
        ft.commit();
        }
    private void gotoAddzara() {
        FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayoutmain4,new AddZaraFragment());
        ft.commit();
    }


    }

