package com.example.breedsapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.breedsapplication.adapter.holder.FavoriteViewHolder;
import com.example.breedsapplication.databinding.ItemFavoriteBinding;

import java.util.List;
import java.util.Map;

public class FavoriteRecyclerViewAdapter extends RecyclerAdapterWithContext<FavoriteViewHolder> {
    private Map<String, List<String>> favorites;
    private OnImageSelected onImageSelected;

    public FavoriteRecyclerViewAdapter(Context context) {
        super(context);
    }

    public FavoriteRecyclerViewAdapter(Context context, Map<String, List<String>> favorites) {
        super(context);
        this.favorites = favorites;
    }

    public Map<String, List<String>> getFavorites() {
        return favorites;
    }

    public void setFavorites(Map<String, List<String>> favorites) {
        this.favorites = favorites;
    }

    public void setOnImageSelected(OnImageSelected onImageSelected) {
        this.onImageSelected = onImageSelected;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoriteBinding binding = ItemFavoriteBinding
                .inflate(getLayoutInflater(), parent, false);
        return new FavoriteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        ItemFavoriteBinding binding = holder.getBinding();
        String breed = (String) favorites.keySet().toArray()[position];
        int imagesCount = favorites.get(breed).size();

        binding.title.setText(breed);
        binding.images.setText(String.valueOf(imagesCount));

        binding.getRoot().setOnClickListener(v -> {
            if (onImageSelected != null) {
                onImageSelected.onImageSelected(position, breed, favorites.get(breed), v);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (favorites == null)
            return 0;
        return favorites.size();
    }

    public interface OnImageSelected {
        void onImageSelected(int pos, String breed, List<String> images, View view);
    }
}
