package com.example.addzara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Fragment HomeFragment;

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

