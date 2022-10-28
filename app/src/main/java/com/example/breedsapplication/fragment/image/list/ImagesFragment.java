package com.example.breedsapplication.fragment.image.list;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breedsapplication.R;
import com.example.breedsapplication.activity.image.ImagesActivity;
import com.example.breedsapplication.adapter.ImageRecyclerViewAdapter;
import com.example.breedsapplication.adapter.decoration.GridSpacingItemDecoration;
import com.example.breedsapplication.databinding.FragmentImagesBinding;
import com.example.breedsapplication.fragment.image.single.ImageFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImagesFragment extends Fragment implements ImageRecyclerViewAdapter.OnItemSelected {
    public static final String BREED_EXTRA_KEY = "Breed";
    public static final String SUB_BREED_EXTRA_KEY = "SubBreed";

    private ImagesViewModel viewModel;
    private FragmentImagesBinding binding;
    private ImageRecyclerViewAdapter adapter;

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

        adapter = new ImageRecyclerViewAdapter(this);
        adapter.setOnItemSelectedListener(this);
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

        // Setup shared element transitions
        prepareTransitions();
        postponeEnterTransition();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Loading images from API
        if (viewModel.getImageList().getValue() == null ||
                viewModel.getImageList().getValue().isEmpty()) {
            executor.execute(this::loadImageList);
        }

        scrollToPosition();
    }

    private void scrollToPosition() {
        // Scroll to image if it's not on the screen
        binding.images.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left,
                                       int top,
                                       int right,
                                       int bottom,
                                       int oldLeft,
                                       int oldTop,
                                       int oldRight,
                                       int oldBottom) {
                binding.images.removeOnLayoutChangeListener(this);
                final RecyclerView.LayoutManager layoutManager = binding.images.getLayoutManager();
                View viewAtPosition = layoutManager.findViewByPosition(ImagesActivity.currentPosition);

                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)) {
                    binding.images.post(() -> layoutManager.scrollToPosition(ImagesActivity.currentPosition));
                }
            }
        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(int pos, String item, List<String> images, View root) {
        ImagesActivity.currentPosition = pos;

        View sharedImageView = root.findViewById(R.id.image);
        String transitionName = ViewCompat.getTransitionName(sharedImageView);
        ImageFragment fragment = ImageFragment.newInstance(item, (ArrayList<String>) images);

        // TODO: move <ImageFragment> to another activity
        getParentFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .addSharedElement(sharedImageView, transitionName)
                .replace(R.id.container, fragment, ImageFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    private void loadImageList() {
        viewModel.loadImageList(breed, subBreed);
    }

    private void prepareTransitions() {
        setExitTransition(TransitionInflater.from(getContext())
                .inflateTransition(R.transition.grid_exit_transition));

        setExitSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        // Locate the ViewHolder for the clicked position.
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
}