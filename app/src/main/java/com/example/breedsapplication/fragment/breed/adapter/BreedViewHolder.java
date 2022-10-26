package com.example.breedsapplication.fragment.breed.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breedsapplication.databinding.ItemBreedBinding;
import com.example.breedsapplication.model.Breed;

public class BreedViewHolder extends RecyclerView.ViewHolder {
    private Breed breed;
    private final ItemBreedBinding binding;

    public BreedViewHolder(@NonNull ItemBreedBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemBreedBinding getBinding() {
        return binding;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }
}
