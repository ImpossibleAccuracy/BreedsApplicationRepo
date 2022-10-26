package com.example.breedsapplication.fragment.image;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.breedsapplication.databinding.FragmentImageBinding;
import com.example.breedsapplication.model.Breed;

public class ImageFragment extends Fragment {
    private ImageViewModel viewModel;
    private FragmentImageBinding binding;

    private String breed;

    public static ImageFragment newInstance(String breed) {
        ImageFragment imageFragment = new ImageFragment();

        Bundle args = new Bundle();
        args.putString(Breed.class.getSimpleName(), breed);

        imageFragment.setArguments(args);
        return imageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        this.viewModel = new ViewModelProvider(this)
                .get(ImageViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            this.breed = args.getString(Breed.class.getSimpleName());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentImageBinding.inflate(inflater, container, false);

        binding.text.setText(breed);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}