package com.example.breedsapplication.fragment.image.list;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breedsapplication.R;
import com.example.breedsapplication.activity.image.ImagesActivity;
import com.example.breedsapplication.adapter.ImageRecyclerViewAdapter;
import com.example.breedsapplication.adapter.decoration.GridSpacingItemDecoration;
import com.example.breedsapplication.adapter.listener.OnImageSelected;
import com.example.breedsapplication.databinding.FragmentImagesBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImagesFragment extends Fragment {
    public static final String BREED_EXTRA_KEY = "Breed";
    public static final String SUB_BREED_EXTRA_KEY = "SubBreed";
    public static final String IMAGES_EXTRA_KEY = "ImagesKey";

    private ImagesViewModel viewModel;
    private FragmentImagesBinding binding;
    private ImageRecyclerViewAdapter adapter;

    private String breed;
    private String subBreed;
    private OnImageSelected onImageSelected;

    private final Executor executor = Executors.newSingleThreadExecutor();

    public static ImagesFragment newInstance(String breed,
                                             String subBreed,
                                             List<String> images) {
        ImagesFragment imageFragment = new ImagesFragment();

        Bundle args = new Bundle();
        args.putString(BREED_EXTRA_KEY, breed);
        args.putString(SUB_BREED_EXTRA_KEY, subBreed);
        args.putStringArrayList(IMAGES_EXTRA_KEY, (ArrayList<String>) images);

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

            List<String> images = args.getStringArrayList(IMAGES_EXTRA_KEY);
            if (images != null) {
                this.viewModel.loadImageList(images);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentImagesBinding.inflate(inflater, container, false);

        adapter = new ImageRecyclerViewAdapter(this);
        adapter.setOnItemSelectedListener(onImageSelected);
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

        prepareTransitions();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Loading images from API
        if (viewModel.getImageList().getValue() == null ||
                viewModel.getImageList().getValue().isEmpty()) {
            update();
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

    private void prepareTransitions() {
        Transition transition =
                TransitionInflater.from(getContext())
                        .inflateTransition(R.transition.grid_exit_transition);
        setExitTransition(transition);

        FragmentActivity activity = requireActivity();
        activity.setExitSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        RecyclerView.ViewHolder selectedViewHolder = binding.images
                                .findViewHolderForAdapterPosition(ImagesActivity.currentPosition);
                        if (selectedViewHolder == null) {
                            return;
                        }

                        // Map the first shared element name to the child ImageView.
                        sharedElements.put(
                                names.get(0),
                                selectedViewHolder.itemView.findViewById(R.id.image));
                    }
                });
    }

    public void update() {
        if (viewModel != null) {
            executor.execute(this::loadImageList);
        }
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSubBreed() {
        return subBreed;
    }

    public void setSubBreed(String subBreed) {
        this.subBreed = subBreed;
    }

    public void setOnItemSelected(OnImageSelected onImageSelected) {
        this.onImageSelected = onImageSelected;
        if (adapter != null) {
            adapter.setOnItemSelectedListener(onImageSelected);
        }
    }
}