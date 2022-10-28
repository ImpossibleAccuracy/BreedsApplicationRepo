package com.example.breedsapplication.fragment.image.single.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class ImageListAdapter extends FragmentStateAdapter {
    private List<String> images;

    public ImageListAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public ImageListAdapter(@NonNull Fragment fragment, List<String> images) {
        super(fragment);
        this.images = images;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return SimpleImageFragment
                .newInstance(images.get(position));
    }

    @Override
    public int getItemCount() {
        if (images == null)
            return 0;
        return images.size();
    }
}
