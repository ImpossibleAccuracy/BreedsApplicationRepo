package com.example.breedsapplication.adapter.listener;

import android.view.View;

import com.example.breedsapplication.model.Breed;

public interface OnBreedSelected {
    void onBreedSelected(int pos, Breed item, View root);
}
