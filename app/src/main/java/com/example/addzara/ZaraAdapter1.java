package com.example.addzara;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

public interface ZaraAdapter1 {
    @NonNull
    ZaraAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    void onBindViewHolder(@NonNull ZaraAdapter.MyViewHolder holder, int position);

    int getItemCount();
}
