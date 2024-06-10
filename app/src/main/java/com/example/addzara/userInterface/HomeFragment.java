package com.example.addzara.userInterface;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.addzara.R;
import com.example.addzara.authentication.FirebaseServices;
import com.google.firebase.auth.FirebaseAuth;

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
    private Handler handler;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView zaralogo;
    private FirebaseServices fbs;
    FirebaseAuth mAuth;
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
        // Move the code that requires getParentFragmentManager() here
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if the fragment is still attached to the activity
                if (isAdded()) {
                    // Switch to another fragment here
                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                    ft.replace(R.id.Framelayoutmain4, new MenuFragment());
                    ft.commit();
                }
            }
        }, 30000); // Delay in milliseconds (30 seconds)
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
        /*handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Switch to another fragment here
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.Framelayoutmain4, new MenuFragment());
                ft.commit();
            }
        }, 30000); // Delay in milliseconds (30 seconds)
*/

    }

};
