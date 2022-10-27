package com.example.breedsapplication.fragment.image.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.breedsapplication.databinding.ItemImageBinding;

import java.util.List;

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private List<String> images;

    private OnItemSelected onItemSelected;
    private final LayoutInflater inflater;

    public ImageRecyclerViewAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public ImageRecyclerViewAdapter(Context context, List<String> images) {
        this(context);
        this.images = images;
    }

    public Context getContext() {
        return inflater.getContext();
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setOnItemSelectedListener(OnItemSelected onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageBinding binding = ItemImageBinding
                .inflate(inflater, parent, false);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String image = images.get(position);
        ItemImageBinding binding = holder.getBinding();

        Glide.with(getContext())
                .load(image)
                .into(binding.image);

        binding.getRoot().setOnClickListener(v -> {
            if (onItemSelected != null) {
                onItemSelected.onItemSelected(position, image, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (images == null)
            return 0;
        return images.size();
    }

    public interface OnItemSelected {
        void onItemSelected(int pos, String item, View root);
    }
}
