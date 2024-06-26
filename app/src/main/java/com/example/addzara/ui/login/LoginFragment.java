package com.example.addzara.ui.login;

import androidx.fragment.app.FragmentTransaction;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.addzara.authentication.FirebaseServices;
import com.example.addzara.authentication.ForgotPasswordFragment;
import com.example.addzara.authentication.SignupFragment;

import com.example.addzara.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginFragment extends Fragment {
        private EditText etUsername,etPassword;
        private Button btnLogin;
        private FirebaseServices fbs;
        private TextView tvSignupLink;
        private TextView forgotpass;

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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_login2, container, false);
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
            forgotpass = getView().findViewById(R.id.tvforgotpasswordLogin);

            tvSignupLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoSignupFragment();

                }
            });
            forgotpass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoForgotPassFragment();

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
        private void gotoForgotPassFragment() {
        FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayoutmain4,new ForgotPasswordFragment());
        ft.commit();
    }
    }