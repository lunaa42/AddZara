package com.example.addzara.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.addzara.R;
import com.example.addzara.addData.User;
import com.example.addzara.authentication.FirebaseServices;
import com.example.addzara.authentication.LoginFragment;
import com.example.addzara.databinding.ActivityMainBinding;
import com.example.addzara.userInterface.FavFragment;
import com.example.addzara.userInterface.HomeFragment;
import com.example.addzara.userInterface.MenuFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private FirebaseServices fbs;
    private Stack<Fragment> fragmentStack = new Stack<>();
    private BottomNavigationView bottomNavigationView;
    private User userData;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        // Access bottomNavigationView after initializing binding
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            // Handle bottom navigation item selection
            if(item.getItemId()== R.id.homenav2){
                replaceFragment(new HomeFragment());
            }
            if(item.getItemId()== R.id.menunav2){
                replaceFragment(new MenuFragment());
            }
            if(item.getItemId()== R.id.profilenav2){
                replaceFragment(new LoginFragment());
            }
            if(item.getItemId()== R.id.favnav2){
                replaceFragment(new FavFragment());
            }

            return true;
        });

              Picasso.setSingletonInstance(new Picasso.Builder(this).build());
                fbs = FirebaseServices.getInstance();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.Framelayoutmain4, new HomeFragment());
                    ft.commit();
    }
    public void onBackPressed() {
        if (fragmentStack.size() > 1) {
            fragmentStack.pop(); // Remove the current fragment from the stack
            Fragment previousFragment = fragmentStack.peek(); // Get the previous fragment

            // Replace the current fragment with the previous one
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.Framelayoutmain4, previousFragment)
                    .commit();
        } else {
            super.onBackPressed(); // If there's only one fragment left, exit the app
        }
    }
    public void pushFragment(Fragment fragment) {
        fragmentStack.push(fragment);

    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Framelayoutmain4,fragment);
        fragmentTransaction.commit();
    }
    public User getUserData()
    {
        final User[] currentUser = {null};
        try {
            fbs.getFire().collection("users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    User user = document.toObject(User.class);
                                    if (fbs.getAuth().getCurrentUser() != null && (fbs.getAuth().getCurrentUser().getEmail().equals(user.getUsername()))) {
                                        //if (fbs.getAuth().getCurrentUser().getEmail().equals(user.getUsername())) {
                                        currentUser[0] = document.toObject(User.class);
                                        fbs.setCurrentUser(currentUser[0]);
                                    }
                                }
                            } else {
                                Log.e("AllRestActivity: readData()", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "error reading!" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return currentUser[0];
    }

    public User getUserDataObject() {
        return this.userData;
    }
}

     /*  FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayoutmain4,new LoginFragment());
        ft.commit();*/

