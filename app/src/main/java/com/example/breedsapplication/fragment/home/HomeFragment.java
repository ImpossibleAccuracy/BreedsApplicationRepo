package com.example.breedsapplication.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.breedsapplication.R;
import com.example.breedsapplication.activity.breed.SubBreedActivity;
import com.example.breedsapplication.activity.image.ImageActivity;
import com.example.breedsapplication.activity.image.ImagesActivity;
import com.example.breedsapplication.adapter.listener.OnBreedSelected;
import com.example.breedsapplication.adapter.listener.OnImageSelected;
import com.example.breedsapplication.adapter.listener.OnSubBreedSelected;
import com.example.breedsapplication.databinding.FragmentHomeBinding;
import com.example.breedsapplication.fragment.breed.BreedFragment;
import com.example.breedsapplication.fragment.image.list.ImagesFragment;
import com.example.breedsapplication.fragment.sub_breed.SubBreedFragment;
import com.example.breedsapplication.model.Breed;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment
        implements OnBreedSelected, OnSubBreedSelected, OnImageSelected {
    private FragmentHomeBinding binding;

    private BreedFragment breedFragment;
    private SubBreedFragment subBreedFragment;
    private ImagesFragment imagesFragment;

    private Breed selectedBreed = null;
    private boolean isTwoFragmentMode = false;
    private Fragment secondaryFragment = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        breedFragment = new BreedFragment();
        subBreedFragment = new SubBreedFragment();
        imagesFragment = new ImagesFragment();

        if (savedInstanceState != null) {
            selectedBreed = (Breed) savedInstanceState
                    .getSerializable(Breed.class.getSimpleName());
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Bundle state;

        if (breedFragment != null) {
            state = new Bundle();
            breedFragment.onSaveInstanceState(state);
            outState.putBundle(BreedFragment.class.getSimpleName(), state);
        }

        if (secondaryFragment != null) {
            state = new Bundle();
            secondaryFragment.onSaveInstanceState(state);
            outState.putBundle(secondaryFragment.getClass().getSimpleName(), state);
        }

        outState.putSerializable(Breed.class.getSimpleName(), selectedBreed);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        checkIsTwoFragmentMode();

        if (!isTwoFragmentMode) {
            WindowCompat.setDecorFitsSystemWindows(
                    requireActivity().getWindow(), false);
        }

        if (savedInstanceState == null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.primary_container, breedFragment,
                            BreedFragment.class.getSimpleName())
                    .commitNow();

            if (isTwoFragmentMode) {
                setSecondaryFragment(subBreedFragment);
                breedFragment.getBreeds().observe(getViewLifecycleOwner(), b -> {
                    if (b != null && !b.isEmpty()) {
                        selectedBreed = b.get(0);
                        subBreedFragment.setBreed(selectedBreed);
                    }
                });
            }
        } else {
            findAllFragments(savedInstanceState);
        }

        breedFragment.setOnItemSelected(this);
        subBreedFragment.setOnItemSelected(this);
        imagesFragment.setOnItemSelected(this);

        return binding.getRoot();
    }

    private void findAllFragments(@NotNull Bundle savedInstanceState) {
        FragmentManager manager = getChildFragmentManager();

        breedFragment = (BreedFragment) manager
                .findFragmentById(R.id.primary_container);
        if (breedFragment == null) {
            throw new NullPointerException("Primary fragment is not found");
        } else {
            Bundle state = savedInstanceState.getBundle(
                    BreedFragment.class.getSimpleName());
            breedFragment.onCreate(state);
        }

        if (isTwoFragmentMode) {
            Fragment fragment = manager.findFragmentById(R.id.secondary_container);

            if (fragment instanceof SubBreedFragment) {
                subBreedFragment = (SubBreedFragment) fragment;

                Bundle state = savedInstanceState.getBundle(
                        SubBreedFragment.class.getSimpleName());

                subBreedFragment.onCreate(state);
            } else if (fragment instanceof ImagesFragment) {
                imagesFragment = (ImagesFragment) fragment;

                Bundle state = savedInstanceState.getBundle(
                        ImagesFragment.class.getSimpleName());

                imagesFragment.onCreate(state);
            }
        }
    }

    private void checkIsTwoFragmentMode() {
        this.isTwoFragmentMode = (binding.secondaryContainer != null);
    }

    private void setSecondaryFragment(Fragment fragment) {
        if (secondaryFragment != fragment) {
            FragmentTransaction transaction = getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.secondary_container, fragment,
                            fragment.getClass().getSimpleName());
            transaction.commit();

            secondaryFragment = fragment;
        }
    }

    @Override
    public void onBreedSelected(int pos, Breed breed, View view) {
        selectedBreed = breed;

        if (isTwoFragmentMode) {
            openNextFragment();
        } else {
            openNextActivity();
        }
    }

    private void openNextActivity() {
        Intent intent;
        if (selectedBreed.getSubBreeds() == null ||
                selectedBreed.getSubBreeds().size() == 0 ||
                selectedBreed.getSubBreeds().isEmpty()) {
            intent = new Intent(getContext(), ImagesActivity.class);
            intent.putExtra(ImagesActivity.BREED_EXTRA_KEY, selectedBreed.getName());
        } else {
            intent = new Intent(getContext(), SubBreedActivity.class);
            intent.putExtra(SubBreedActivity.BREED_EXTRA_KEY, selectedBreed);
        }
        startActivity(intent);
    }

    private void openNextFragment() {
        if (selectedBreed.getSubBreeds() == null || selectedBreed.getSubBreeds().isEmpty()) {
            imagesFragment.setBreed(selectedBreed.getName());
            imagesFragment.setSubBreed(null);

            setSecondaryFragment(imagesFragment);
            imagesFragment.update();
        } else {
            setSecondaryFragment(subBreedFragment);
            subBreedFragment.setBreed(selectedBreed);
        }
    }

    @Override
    public void onSubBreedSelected(int pos, String item, View view) {
        imagesFragment.setBreed(selectedBreed.getName());
        imagesFragment.setSubBreed(item);

        setSecondaryFragment(imagesFragment);
        imagesFragment.update();
    }

    @Override
    public void onImageSelected(int pos, String item, List<String> images, View root) {
        ImagesActivity.currentPosition = pos;

        Activity activity = requireActivity();
        View sharedImageView = root.findViewById(R.id.image);
        String transitionName = ViewCompat.getTransitionName(sharedImageView);

        Intent intent = new Intent(activity, ImageActivity.class);
        intent.putExtra(ImageActivity.IMAGE_EXTRA_KEY, item);
        intent.putStringArrayListExtra(ImageActivity.IMAGE_LIST_EXTRA_KEY, (ArrayList<String>) images);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, sharedImageView, transitionName);

        startActivity(intent, options.toBundle());
    }
}