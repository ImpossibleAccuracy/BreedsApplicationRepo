package com.example.breedsapplication.fragment.image.single;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
import com.example.breedsapplication.databinding.FragmentImageItemBinding;

public class ImageItemFragment extends Fragment {
    private static final String IMAGE_EXTRA_KEY = "Image";

    private FragmentImageItemBinding binding;

    private String image;

    public static ImageItemFragment newInstance(String image) {
        ImageItemFragment fragment = new ImageItemFragment();

        Bundle args = new Bundle();
        args.putString(IMAGE_EXTRA_KEY, image);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            this.image = args.getString(IMAGE_EXTRA_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentImageItemBinding.inflate(inflater);

        String tName = ("TR_IMAGE_" + image);
        ViewCompat.setTransitionName(binding.image, tName);

        // Start transition when image is ready
        Glide.with(requireContext())
                .load(image)
                .listener(new RequestListener<>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model,
                                                Target<Drawable> target,
                                                boolean isFirstResource) {
                        onImageLoaded();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource,
                                                   Object model,
                                                   Target<Drawable> target,
                                                   DataSource dataSource,
                                                   boolean isFirstResource) {
                        onImageLoaded();
                        return false;
                    }
                })
                .into(binding.image);

        return binding.getRoot();
    }

    private void onImageLoaded() {
        Fragment parentFragment = requireParentFragment();
        parentFragment.getActivity().startPostponedEnterTransition();
    }
}
