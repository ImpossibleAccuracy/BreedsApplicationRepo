package com.example.breedsapplication.fragment.image.single.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.breedsapplication.databinding.FragmentSimpleImageBinding;

public class SimpleImageFragment extends Fragment {
    private static final String IMAGE_EXTRA_KEY = "Image";

    private FragmentSimpleImageBinding binding;

    private String image;

    public static SimpleImageFragment newInstance(String image) {
        SimpleImageFragment fragment = new SimpleImageFragment();

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
        binding = FragmentSimpleImageBinding.inflate(inflater);

        ViewCompat.setTransitionName(
                binding.image, "TR_IMAGE_" + image);

        Glide.with(requireContext())
                .load(image)
                .into(binding.image);

        return binding.getRoot();
    }
}
