package com.example.addzara;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.addzara.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BottomNavigationView bottomNavigationView;
    private ImageView zaralogo;
    private FirebaseServices fbs;
    private VideoView v4;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize VideoView
        v4 = view.findViewById(R.id.vid4);

        // Set video file path
        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.vid4;
        v4.setVideoURI(Uri.parse(videoPath));

        // Start video playback
        v4.start();
/*
        // Find and initialize the BottomNavigationView
        bottomNavigationView = view.findViewById(R.id.bottomnavHome);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                boolean itemSelected = false;
                if (item.getItemId() == R.id.homenav2) {
                    selectedFragment = new HomeFragment();
                    itemSelected = true;
                } else if (item.getItemId() == R.id.menunav2) {
                    selectedFragment = new MenuFragment();
                    itemSelected = true;
                } else if (item.getItemId() == R.id.profilenav2) {
                    selectedFragment = new LoginFragment();
                    itemSelected = true;
                } else if (item.getItemId() == R.id.favnav2) {
                    selectedFragment = new FavFragment();
                    itemSelected = true;
                }

                if (selectedFragment != null) {
                    // Replace the current fragment with the selected one
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Framelayoutmain4, selectedFragment)
                            .addToBackStack(null) // Optional: Add to back stack if you want to navigate back
                            .commit();
                }

                // Check if the item was selected
                if (itemSelected) {
                    // Set the selected item in the bottom navigation bar
                    item.setChecked(true);
                }

                return true;
            }
        }); */

        return view;
    }






    // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_home, container, false);


    public void onStart() {
        super.onStart();
        // connecting components
        fbs = FirebaseServices.getInstance();
        zaralogo = getView().findViewById(R.id.zaralogohome);
        v4 = getView().findViewById(R.id.vid4);
        // Get the layout parameters of the VideoView
        ViewGroup.LayoutParams layoutParams = v4.getLayoutParams();

// Adjust the layout parameters to match the screen dimensions
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

// Set the updated layout parameters back to the VideoView
        v4.setLayoutParams(layoutParams);
        String path = "android.resource://com.example.addzara/"+R.raw.vid4;
        Uri u = Uri.parse(path);
        v4.setVideoURI(u);
        v4.start();
        v4.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        /*@Override
        protected void onResume();{
            v4.resume();
            super.onResume();
        }
        @Override
                protected void onPause(){
            v4.suspend();
            super.onPause();
        };
        @Override
        protected void onDestroy(){
            v4.stopPlayback();
            super.onDestroy();
        }*/
// Find and initialize the BottomNavigationView
        bottomNavigationView = getActivity().findViewById(R.id.bottomnavHome);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
           @SuppressLint("NonConstantResourceId")
            @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
               if (selectedFragment != null) {
                   requireActivity().getSupportFragmentManager().beginTransaction()
                           .replace(R.id.Framelayoutmain4, selectedFragment)
                           .addToBackStack(null)
                           .commit();

                   // Select the corresponding item in the bottom navigation
                   switch (selectedFragment.getClass().getSimpleName()) {
                       case "HomeFragment":
                           bottomNavigationView.setSelectedItemId(R.id.homenav2);
                           break;
                       case "MenuFragment":
                           bottomNavigationView.setSelectedItemId(R.id.menunav2);
                           break;
                       case "LoginFragment":
                           bottomNavigationView.setSelectedItemId(R.id.profilenav2);
                           break;
                       case "FavFragment":
                           bottomNavigationView.setSelectedItemId(R.id.favnav2);
                           break;
                   }
               }
                // Check if the item was selected

                return true;
            }
        });

        //bottomNavigationView.setSelectedItemId();
    }

};
