package com.example.breedsapplication.fragment.image;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.breedsapplication.R;
import com.example.breedsapplication.databinding.FragmentImagesBinding;
import com.example.breedsapplication.fragment.image.adapter.GridSpacingItemDecoration;
import com.example.breedsapplication.fragment.image.adapter.ImageRecyclerViewAdapter;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImagesFragment extends Fragment {
    public static final String BREED_EXTRA_KEY = "Breed";
    public static final String SUB_BREED_EXTRA_KEY = "SubBreed";

    private ImagesViewModel viewModel;
    private FragmentImagesBinding binding;
    private ImageRecyclerViewAdapter adapter;
    private ImageRecyclerViewAdapter.OnItemSelected onItemSelected;

    private String breed;
    private String subBreed;

    private final Executor executor = Executors.newSingleThreadExecutor();

    public static ImagesFragment newInstance(String breed, String subBreed) {
        ImagesFragment imageFragment = new ImagesFragment();

        Bundle args = new Bundle();
        args.putString(BREED_EXTRA_KEY, breed);
        args.putString(SUB_BREED_EXTRA_KEY, subBreed);

        imageFragment.setArguments(args);
        return imageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        this.viewModel = new ViewModelProvider(this)
                .get(ImagesViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            this.breed = args.getString(BREED_EXTRA_KEY);
            this.subBreed = args.getString(SUB_BREED_EXTRA_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentImagesBinding.inflate(inflater, container, false);

        adapter = new ImageRecyclerViewAdapter(getContext());
        adapter.setOnItemSelectedListener(onItemSelected);
        viewModel.getImageList().observe(getViewLifecycleOwner(), this::onImagesLoaded);

        binding.images.setAdapter(adapter);
        binding.images.setLayoutManager(
                new GridLayoutManager(
                        getContext(), 2));
        binding.images.addItemDecoration(
                new GridSpacingItemDecoration(
                        2,
                        (int) requireContext().getResources().getDimension(R.dimen.grid_spacing),
                        true));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (viewModel.getImageList().getValue() == null ||
                viewModel.getImageList().getValue().isEmpty()) {
            executor.execute(this::loadImageList);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void onImagesLoaded(List<String> images) {
        if (images != null) {
            if (images.isEmpty()) {
                binding.images.setVisibility(View.GONE);
                binding.emptyView.setVisibility(View.VISIBLE);
            } else {
                binding.images.setVisibility(View.VISIBLE);
                binding.emptyView.setVisibility(View.GONE);

                adapter.setImages(images);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void loadImageList() {
        viewModel.loadImageList(breed, subBreed);
    }

    public void setOnItemSelected(ImageRecyclerViewAdapter.OnItemSelected onItemSelected) {
        this.onItemSelected = onItemSelected;
        if (adapter != null) {
            adapter.setOnItemSelectedListener(onItemSelected);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}