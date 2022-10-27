package com.example.breedsapplication.fragment.image.single;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.breedsapplication.databinding.FragmentImageBinding;

public class ImageFragment extends Fragment {
    public static final String IMAGE_EXTRA_KEY = "Image";
    public static final String TRANSITION_EXTRA_KEY = "Transition";

    private FragmentImageBinding binding;

    private String image;
    private String transitionName;

    public static ImageFragment newInstance(String image, String transitionName) {
        ImageFragment fragment = new ImageFragment();

        Bundle args = new Bundle();
        args.putString(IMAGE_EXTRA_KEY, image);
        args.putString(TRANSITION_EXTRA_KEY, transitionName);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            image = args.getString(IMAGE_EXTRA_KEY);
            transitionName = args.getString(TRANSITION_EXTRA_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentImageBinding.inflate(inflater);

        ViewCompat.setTransitionName(binding.image, transitionName);

        Glide.with(requireContext())
                .load(image)
                .into(binding.image);

        return binding.getRoot();
    }
}
