package com.example.addzara;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    private static final int PERMISSION_SEND_SMS = 1;
    private static final int REQUEST_CALL_PERMISSION = 2;
    private FirebaseServices fbs;
    private TextView tvproduct,tvsize,tvcolour,tvdescription,tvprice;
    private ImageView ivproductPhoto;
    private ZaraItem myproduct;
    private Button btnBuy;
    private boolean isEnlarged = false;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
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
        return inflater.inflate(R.layout.fragment_details, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        init();
        ImageView ivproductphoto4 = getView().findViewById(R.id.ivProductdeta);

        ivproductphoto4.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                ViewGroup.LayoutParams layoutParams = ivproductphoto4.getLayoutParams();
                if (isEnlarged) {
                    layoutParams.height =500;
                } else {
                    layoutParams.height = 2200;
                }
                ivproductphoto4.setLayoutParams(layoutParams);

                // נשנה את המצב הנוכחי של התמונה
                isEnlarged = !isEnlarged;

            }
        });


    }
    public void init()
    {


        fbs= FirebaseServices.getInstance();
        tvproduct=getView().findViewById(R.id.tvproduct2Deta);
        tvsize=getView().findViewById(R.id.tvsize2Deta);
        tvcolour=getView().findViewById(R.id.tvcolour2Deta);
        tvprice=getView().findViewById(R.id.tvprice2Deta);
        tvdescription=getView().findViewById(R.id.tvdescri2Deta);
        ivproductPhoto = getView().findViewById(R.id.ivProductdeta);

        Bundle args = getArguments();
        if (args != null) {
            myproduct = args.getParcelable("product");
            if (myproduct != null) {
                //String data = myObject.getData();
                // Now you can use 'data' as needed in FragmentB
                tvproduct.setText(myproduct.getProduct());
                tvsize.setText(myproduct.getSize());
                tvcolour.setText(myproduct.getColour());
                tvprice.setText(myproduct.getPrice() + " ₪");
                tvdescription.setText(myproduct.getDescription());
                if (myproduct.getPhoto() == null || myproduct.getPhoto().isEmpty())
                {
                    Picasso.get().load(R.drawable.clothinhw1_removebg_preview).into(ivproductPhoto);
                }
                else {
                    Picasso.get().load(myproduct.getPhoto()).into(ivproductPhoto);
                }
            }
        }
        btnBuy = getView().findViewById(R.id.btnBuydetail);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*gotobuy();*/            }
        });

    }

}