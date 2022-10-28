package com.example.breedsapplication.adapter.holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breedsapplication.databinding.ItemImageBinding;

public class ImageViewHolder extends RecyclerView.ViewHolder {
    private final ItemImageBinding binding;

    public ImageViewHolder(@NonNull ItemImageBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemImageBinding getBinding() {
        return binding;
    }
}
