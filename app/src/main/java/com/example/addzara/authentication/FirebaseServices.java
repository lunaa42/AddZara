package com.example.addzara.authentication;



import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.addzara.addData.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class FirebaseServices {
    private static FirebaseServices instance;
    private FirebaseAuth auth;
    private FirebaseFirestore fire;
    private FirebaseStorage storage;
    private User currentUser;
    private Uri selectedImageURL;

    public FirebaseServices(){
        auth = FirebaseAuth.getInstance();
        fire = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }
    public static FirebaseServices getInstance() {
        if (instance == null)
            instance = new FirebaseServices();
        return instance;
    }
    public Uri getSelectedImageURL() {
        return selectedImageURL;
    }
    public static FirebaseServices reloadInstance(){
        instance=new FirebaseServices();
        return instance;
    }

    public void setSelectedImageURL(Uri selectedImageURL) {
        this.selectedImageURL = selectedImageURL;
    }
    public User getCurrentUser()
    {
        return this.currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseFirestore getFire() {
        return fire;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }
    public void updateUserFavorites(String userId, ArrayList<String> favorites) {
        // Assuming you have a "users" collection in your Firestore database
        // Update the "favorites" field for the user document with the specified userId

        // Get a reference to the Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Reference to the user document
        DocumentReference userRef = db.collection("users").document(userId);

        // Update the "favorites" field in the user document
        userRef
                .update("favorites", favorites)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Handle success

                    //    Log.d(TAG, "User favorites updated successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure
                        //Log.d(TAG, "Error updating user favorites", e);
                    }
                });
    }
}
