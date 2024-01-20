package com.example.addzara;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddZaraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddZaraFragment extends Fragment {
    private FirebaseServices fbs;
    private EditText etProduct,etSize,etcolour,etPhone;
    private Button btnAdd;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddZaraFragment() {
        // Required empty public constructor
    }

    public AddZaraFragment(String product, String size, String address, String phone) {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddZaraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddZaraFragment newInstance(String param1, String param2) {
        AddZaraFragment fragment = new AddZaraFragment();
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
        return inflater.inflate(R.layout.fragment_add_zara, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        connectComponents();
    }


    private void connectComponents() {
        fbs = FirebaseServices.getInstance();
        etProduct = getView().findViewById(R.id.etProductAddZaraFragment);
        etSize = getView().findViewById(R.id.etSIzeAddZaraFragment);
        etcolour = getView().findViewById(R.id.etColourAddZaraFragment);
        etPhone = getView().findViewById(R.id.etPhoneAddZaraFragment);
        btnAdd = getView().findViewById(R.id.btnAddAddZaraFragment);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: add data to firebase
                // get data from screen
                String product = etProduct.getText().toString();
                String size = etSize.getText().toString();
                String colour = etcolour.getText().toString();
                String phone = etPhone.getText().toString();


                if (product.isEmpty() || size.isEmpty() ||
                        colour.isEmpty() || phone.isEmpty())
                {
                    Toast.makeText(getActivity(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
                    return;
                }


                Zara zara = new Zara(product, size, colour, phone);


                fbs.getFire().collection("products").add(zara).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Successfully added your product!", Toast.LENGTH_SHORT).show();
                           FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.Framelayoutmain4,new AllZaraFragment());
                            ft.commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Failure AddZara: ", e.getMessage());

                    }
                });

            }

        });
    }


}

