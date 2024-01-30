package com.example.addzara;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddZaraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddZaraFragment extends Fragment {
    private FirebaseServices fbs;
    private EditText etProduct, etSize, etcolour, etPrice, etDescripton;
    private static final int GALLERY_REQUEST_CODE = 123;
    private ImageView img;
    private String imageStr;
    private Button btnAdd;
    private Utils utils;

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
        utils = Utils.getInstance();
        etProduct = getView().findViewById(R.id.etProductAddZaraFragment);
        etSize = getView().findViewById(R.id.etSIzeAddZaraFragment);
        etcolour = getView().findViewById(R.id.etColourAddZaraFragment);
        etPrice = getView().findViewById(R.id.etPriceAddZaraFragment);
        btnAdd = getView().findViewById(R.id.btnAddAddZaraFragment);
        etDescripton = getView().findViewById(R.id.etDescriptionAddZaraFragment);
        img = getView().findViewById(R.id.ivImgAddZaraFragment);
// Enable the button
        btnAdd.setEnabled(true);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OnClickListener", "Button clicked");
                addToFirestore();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        ((MainActivity) getActivity()).pushFragment(new AddZaraFragment());
    }


    public void addToFirestore() {
        Log.d("addToFirestore", "Started");
            String productname, size, colour, description,price ;

//get data from screen

            productname=etProduct.getText().toString();
            size=etSize.getText().toString();
            colour=etcolour.getText().toString();
            description = etDescripton.getText().toString();
            price=etPrice.getText().toString();

            if (productname.trim().isEmpty() ||
                    size.trim().isEmpty() ||
                    colour.trim().isEmpty()||
                    description.trim().isEmpty()||
                    price.trim().isEmpty())

            {
                Toast.makeText(getActivity(), "sorry some data missing incorrect !", Toast.LENGTH_SHORT).show();
                return;
            }

            Zara product1;
            ZaraItem product2;
            if (fbs.getSelectedImageURL() == null)
            {
                product1= new Zara(productname, size, colour, price, description ,"");
                product2 = new ZaraItem(productname, size, colour, price, description ,"");
            }
            else {
                product1= new Zara(productname, size, colour, price, description , fbs.getSelectedImageURL().toString());
                product2 = new ZaraItem(productname, size, colour, price, description ,fbs.getSelectedImageURL().toString());

            }

            fbs.getFire().collection("products").add(product1)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getActivity(), "ADD product is Succeed ", Toast.LENGTH_SHORT).show();
                            Log.e("addToFirestore() - add to collection: ", "Successful!");
                            gotomenu();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@androidx.annotation.NonNull Exception e) {
                            Log.e("addToFirestore() - add to collection: ", e.getMessage());
                        }
                    });

            try {
                fbs.getFire().collection("product2").add(product2)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                //Toast.makeText(getActivity(), "ADD Car is Succesed ", Toast.LENGTH_SHORT).show();
                                Log.e("addToFirestore() - add to collection: ", "Successful!");
                                gotomenu();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@androidx.annotation.NonNull Exception e) {
                                Log.e("addToFirestore() - add to collection: ", e.getMessage());
                            }
                        });
            }
            catch (Exception ex)
            {
                Log.e("AddZaraFragment: addToFirestore()", ex.getMessage());
            }
        }


    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            img.setImageURI(selectedImageUri);
            utils.uploadImage(getActivity(), selectedImageUri);
        }
    }
    public void gotomenu(){
        FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayoutmain4,new MenuFragment());
        ft.commit();
    }
/*
  public void uploadImage(Uri selectedImageUri) {
        if (selectedImageUri != null) {
            imageStr = "images/" + UUID.randomUUID() + ".jpg"; //+ selectedImageUri.getLastPathSegment();
            StorageReference imageRef = fbs.getStorage().getReference().child(imageStr);

            if (selectedImageUri != null) {
                Log.d("ImageUpload", "Selected Image URI: " + selectedImageUri.toString());
                UploadTask uploadTask = imageRef.putFile(selectedImageUri);
                Log.d("ImageUpload", "Firebase Storage Path: " + imageStr);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //selectedImageUri = uri;
                                fbs.setSelectedImageURL(uri);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@androidx.annotation.NonNull Exception e) {

                            }
                        });
                        Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@androidx.annotation.NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please choose an image first", Toast.LENGTH_SHORT).show();
            }
        }

    }*/
}


