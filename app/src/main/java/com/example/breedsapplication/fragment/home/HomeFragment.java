package com.example.breedsapplication.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.breedsapplication.R;
import com.example.breedsapplication.activity.breed.SubBreedActivity;
import com.example.breedsapplication.activity.image.ImagesActivity;
import com.example.breedsapplication.databinding.FragmentHomeBinding;
import com.example.breedsapplication.fragment.breed.BreedFragment;
import com.example.breedsapplication.fragment.image.list.ImagesFragment;
import com.example.breedsapplication.fragment.sub_breed.SubBreedFragment;
import com.example.breedsapplication.model.Breed;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    private BreedFragment breedFragment;
    private SubBreedFragment subBreedFragment;
    private ImagesFragment imagesFragment;

    private Fragment secondaryFragment = null;

    private Breed selectedBreed = null;
    private boolean isTwoFragmentMode = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        breedFragment = new BreedFragment();
        subBreedFragment = new SubBreedFragment();
        imagesFragment = new ImagesFragment();

        breedFragment.setOnItemSelected(this::onBreedSelected);
        subBreedFragment.setOnItemSelected(this::onSubBreedSelected);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        checkIsTwoFragmentMode();

        getChildFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.primary_container,
                        breedFragment,
                        BreedFragment.class.getSimpleName())
                .commit();
        if (isTwoFragmentMode) {
            setSecondaryFragment(subBreedFragment);
        }

        return binding.getRoot();
    }

    private void checkIsTwoFragmentMode() {
        this.isTwoFragmentMode = (binding.secondaryContainer != null);
    }

    private void onBreedSelected(int pos, Breed breed, View view) {
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
        } else {
            setSecondaryFragment(subBreedFragment);
            subBreedFragment.setBreed(selectedBreed);
        }
    }

    private void onSubBreedSelected(int pos, String item, View view) {
        imagesFragment.setBreed(selectedBreed.getName());
        imagesFragment.setSubBreed(item);

        setSecondaryFragment(imagesFragment);
    }

    private void setSecondaryFragment(Fragment fragment) {
        if (secondaryFragment != fragment) {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.secondary_container,
                            fragment,
                            fragment.getClass().getSimpleName())
                    .commit();

            secondaryFragment = fragment;
        }
    }
}