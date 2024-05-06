package com.example.addzara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.addzara.R;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.addzara.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private FirebaseServices fbs;
    private Stack<Fragment> fragmentStack = new Stack<>();
    private BottomNavigationView bottomNavigationView;
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
}



     /*  FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayoutmain4,new LoginFragment());
        ft.commit();*/

