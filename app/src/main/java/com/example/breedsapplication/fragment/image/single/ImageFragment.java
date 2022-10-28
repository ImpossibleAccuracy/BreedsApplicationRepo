package com.example.breedsapplication.fragment.image.single;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.breedsapplication.databinding.FragmentImageBinding;
import com.example.breedsapplication.fragment.image.single.adapter.ImageListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageFragment extends Fragment {
    private static final String IMAGE_EXTRA_KEY = "Image";
    private static final String IMAGE_LIST_EXTRA_KEY = "ImageList";

    private ImageListAdapter adapter;
    private FragmentImageBinding binding;

    private String image;
    private List<String> images;

    public static ImageFragment newInstance(String image, ArrayList<String> images) {
        ImageFragment fragment = new ImageFragment();

        Bundle args = new Bundle();
        args.putString(IMAGE_EXTRA_KEY, image);
        args.putStringArrayList(IMAGE_LIST_EXTRA_KEY, images);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            image = args.getString(IMAGE_EXTRA_KEY);
            images = args.getStringArrayList(IMAGE_LIST_EXTRA_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentImageBinding.inflate(inflater);

        adapter = new ImageListAdapter(this);
        adapter.setImages(images);

        binding.pager.setAdapter(adapter);
        binding.pager.setCurrentItem(images.indexOf(image));

        /*ViewCompat.setTransitionName(binding.image, transitionName);

        Glide.with(requireContext())
                .load(image)
                .into(binding.image);*/

        return binding.getRoot();
    }
}
