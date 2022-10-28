package com.example.breedsapplication.adapter.holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breedsapplication.databinding.ItemSubBreedBinding;
import com.example.breedsapplication.model.Breed;

public class SubBreedViewHolder extends RecyclerView.ViewHolder {
    private final ItemSubBreedBinding binding;

    public SubBreedViewHolder(@NonNull ItemSubBreedBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemSubBreedBinding getBinding() {
        return binding;
    }
}
