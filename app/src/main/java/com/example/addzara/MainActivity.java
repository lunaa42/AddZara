package com.example.addzara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private Fragment HomeFragment;
    private FirebaseServices fbs;
    private BottomNavigationView bottomNavigationView;
    @Override
            protected void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.Framelayoutmain4,new HomeFragment());
                ft.commit();
    }

        }



     /*  FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayoutmain4,new LoginFragment());
        ft.commit();*/

