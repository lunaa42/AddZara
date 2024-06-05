package com.example.addzara;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.addzara.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class PaymentBottomSheetFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet, container, false);

        EditText email = view.findViewById(R.id.edit_text_email);
        EditText cardNumber = view.findViewById(R.id.edit_text_card_number);
        EditText expiryDate = view.findViewById(R.id.edit_text_expiry_date);
        EditText cvv = view.findViewById(R.id.edit_text_cvv);
        EditText nameOnCard = view.findViewById(R.id.edit_text_name_on_card);
        Spinner country = view.findViewById(R.id.spinner_country);
        EditText zip = view.findViewById(R.id.edit_text_zip);
        Button submitButton = view.findViewById(R.id.btn_submit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.countries_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(adapter);
        // Populate the spinner with country data (this is a placeholder, implement as needed)
        // ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, countries);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // country.setAdapter(adapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle payment submission
                String emailText = email.getText().toString();
                String cardNum = cardNumber.getText().toString();
                String expDate = expiryDate.getText().toString();
                String cvvCode = cvv.getText().toString();
                String name = nameOnCard.getText().toString();
                String countryText = country.getSelectedItem().toString();
                String zipCode = zip.getText().toString();
                // You can add more validation and processing logic here
                Toast.makeText(getContext(), "Payment Submitted", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return view;
    }
}
