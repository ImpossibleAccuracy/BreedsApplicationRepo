package com.example.breedsapplication.adapter.holder;

import androidx.recyclerview.widget.RecyclerView;

import com.example.breedsapplication.databinding.ItemFavoriteBinding;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {
    private final ItemFavoriteBinding binding;

    public FavoriteViewHolder(ItemFavoriteBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemFavoriteBinding getBinding() {
        return binding;
    }
}
