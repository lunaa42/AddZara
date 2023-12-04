package com.example.addzara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                // Wait for 3 seconds before performing the fragment transaction
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Call a method to perform your fragment transaction
                        performFragmentTransaction();
                    }
                }, 3000); // 3000 milliseconds = 3 seconds
            }

            private void performFragmentTransaction() {
                // Create a new instance of your fragment
                LoginFragment fragment = new LoginFragment();

                // Get the FragmentManager
                FragmentManager fragmentManager = getSupportFragmentManager();

                // Start a new FragmentTransaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the existing fragment with the new one
                fragmentTransaction.replace(R.id.Framelayoutmain4, fragment);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        }



     /*  FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayoutmain4,new LoginFragment());
        ft.commit();*/

