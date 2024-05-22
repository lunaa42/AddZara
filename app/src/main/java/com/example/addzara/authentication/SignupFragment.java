package com.example.addzara.authentication;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.addzara.R;
import com.example.addzara.addData.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {
    private EditText etUsername, etPassword, firstNameEditText, lastNameEditText, etPhone;
    private Button btnSignup;
    private FirebaseServices fbs;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ImageView backsymbSignup;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // connecting components
        fbs =  FirebaseServices.getInstance();
        etUsername = getView().findViewById(R.id.etUsernameSignUp);
        etPassword = getView().findViewById(R.id.etPasswordSignUp);
        btnSignup = getView().findViewById(R.id.btSignupSignup);
        backsymbSignup = getView().findViewById(R.id.imgbacksignup);
        firstNameEditText = getView().findViewById(R.id.firstnamesignup);
        lastNameEditText = getView().findViewById(R.id.lastnamesignup);
        etPhone = getView().findViewById(R.id.phoneSign);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Data validation
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String firstname = firstNameEditText.getText().toString();
                String lastname = lastNameEditText.getText().toString();
                String phone = etPhone.getText().toString();
                if (mAuth != null) {
                    signUpUser(username, password, firstname, lastname, phone);
                } else {
                    Log.e(TAG, "FirebaseAuth instance is null");
                }                if (username.trim().isEmpty() || password.trim().isEmpty() || firstname.trim().isEmpty() ||
                        lastname.trim().isEmpty() ||  phone.trim().isEmpty())
                {
                    Toast.makeText(getActivity(), "some fields are empty", Toast.LENGTH_SHORT).show();
                    return;

                }
                String uid = fbs.getAuth().getCurrentUser().getUid();
                User user = new User(firstname, lastname, username, phone);

                // Signup procedure
                /*
                Task<AuthResult> authResultTask = fbs.getAuth().createUserWithEmailAndPassword(username, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            fbs.getFire().collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    gotoLogin();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("SignupFragment: signupOnClick: ", e.getMessage());
                                }
                            });
                            //
                            Toast.makeText(getActivity(), "You have successfully signed up!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "Failed to sign up! Check user or password..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
*/
            }
        });
        backsymbSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.Framelayoutmain4, new LoginFragment());
                ft.commit();
            }
    });
}

    private void signUpUser(String username, String password, String firstname, String lastname, String phone) {

        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String uid = user.getUid();
                            Log.d(TAG, "Signup successful, UID: " + uid);

                            // Create a user object to store in Firestore
                            User newUser = new User(firstname, lastname, username, phone);

                            // Save user to Firestore
                            db.collection("users").document(uid).set(newUser)
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "User saved to Firestore"))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error saving user to Firestore", e));
                        } else {
                            Log.e(TAG, "FirebaseUser is null after signup");
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    }
                });
    }

    private void gotoLogin() {
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayoutmain4,new LoginFragment());
        ft.commit();
    }
}
