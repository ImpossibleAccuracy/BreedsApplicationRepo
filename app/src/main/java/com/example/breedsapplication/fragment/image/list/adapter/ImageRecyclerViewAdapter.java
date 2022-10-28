package com.example.breedsapplication.fragment.image.list.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.breedsapplication.activity.image.ImagesActivity;
import com.example.breedsapplication.databinding.ItemImageBinding;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private List<String> images;
    private OnItemSelected onItemSelected;

    private final Fragment fragment;
    private final LayoutInflater inflater;
    private final AtomicBoolean enterTransitionStarted = new AtomicBoolean(false);

    public ImageRecyclerViewAdapter(Fragment fragment) {
        this.inflater = LayoutInflater.from(fragment.getContext());
        this.fragment = fragment;
    }

    public ImageRecyclerViewAdapter(Fragment fragment, List<String> images) {
        this(fragment);
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

        String tName = ("TR_IMAGE_" + image);
        ViewCompat.setTransitionName(binding.image, tName);

        // Start transition when image is ready
        Glide.with(getContext())
                .load(image)
                .listener(new RequestListener<>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        onLoadCompleted(holder.getAdapterPosition());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
                            target, DataSource dataSource, boolean isFirstResource) {
                        onLoadCompleted(holder.getAdapterPosition());
                        return false;
                    }
                })
                .into(binding.image);

        binding.getRoot().setOnClickListener(v -> {
            if (onItemSelected != null) {
                onItemSelected.onItemSelected(position, image, images, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (images == null)
            return 0;
        return images.size();
    }

    public void onLoadCompleted(int position) {
        if (ImagesActivity.currentPosition != position) {
            return;
        }
        if (enterTransitionStarted.getAndSet(true)) {
            return;
        }
        fragment.startPostponedEnterTransition();
    }

    public interface OnItemSelected {
        void onItemSelected(int pos, String item, List<String> images, View root);
    }
}
