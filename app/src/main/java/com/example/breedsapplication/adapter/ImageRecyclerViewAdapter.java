package com.example.breedsapplication.adapter;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.breedsapplication.activity.image.ImagesActivity;
import com.example.breedsapplication.adapter.holder.ImageViewHolder;
import com.example.breedsapplication.adapter.listener.OnImageSelected;
import com.example.breedsapplication.databinding.ItemImageBinding;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImageRecyclerViewAdapter extends RecyclerAdapterWithContext<ImageViewHolder> {
    private List<String> images;
    private OnImageSelected onImageSelected;

    private final Fragment fragment;
    private final AtomicBoolean enterTransitionStarted = new AtomicBoolean(false);

    public ImageRecyclerViewAdapter(Fragment fragment) {
        super(fragment.getContext());
        this.fragment = fragment;
    }

    public ImageRecyclerViewAdapter(Fragment fragment, List<String> images) {
        this(fragment);
        this.images = images;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setOnItemSelectedListener(OnImageSelected onImageSelected) {
        this.onImageSelected = onImageSelected;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageBinding binding = ItemImageBinding
                .inflate(getLayoutInflater(), parent, false);
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
            if (onImageSelected != null) {
                onImageSelected.onImageSelected(position, image, images, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (images == null)
            return 0;
        return images.size();
    }

    private void onLoadCompleted(int position) {
        if (ImagesActivity.currentPosition != position) {
            return;
        }
        if (enterTransitionStarted.getAndSet(true)) {
            return;
        }
        fragment.getActivity().startPostponedEnterTransition();
    }
}
