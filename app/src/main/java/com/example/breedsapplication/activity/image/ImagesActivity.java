package com.example.breedsapplication.activity.image;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import com.example.breedsapplication.R;
import com.example.breedsapplication.activity.AppActivity;
import com.example.breedsapplication.adapter.listener.OnImageSelected;
import com.example.breedsapplication.databinding.ActivityImagesBinding;
import com.example.breedsapplication.fragment.image.list.ImagesFragment;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppActivity implements OnImageSelected {
    private static final String CURRENT_POS_KEY = "CurrentPos";

    public static final String BREED_EXTRA_KEY = "BreedKey";
    public static final String SUB_BREED_EXTRA_KEY = "SubBreedKey";
    public static final String IMAGES_EXTRA_KEY = "ImagesKey";

    public static int currentPosition;

    private ActivityImagesBinding binding;
    private ImagesFragment imagesFragment;

    private String breed;
    private String subBreed;
    private List<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar(this.breed);

        if (savedInstanceState == null) {
            ImagesActivity.currentPosition = 0;

            imagesFragment = ImagesFragment.newInstance(breed, subBreed, images);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, imagesFragment, ImagesFragment.class.getSimpleName())
                    .commit();
        } else {
            ImagesActivity.currentPosition =
                    savedInstanceState.getInt(CURRENT_POS_KEY, 0);

            imagesFragment = (ImagesFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.container);

            if (imagesFragment == null)
                throw new NullPointerException("Fragment not found.");
        }

        imagesFragment.setOnItemSelected(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_POS_KEY, ImagesActivity.currentPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void parseIntent(Intent intent) {
        this.breed = intent.getStringExtra(BREED_EXTRA_KEY);
        this.subBreed = intent.getStringExtra(SUB_BREED_EXTRA_KEY);
        this.images = intent.getStringArrayListExtra(IMAGES_EXTRA_KEY);
    }

    @Override
    public void onImageSelected(int pos, String item, List<String> images, View root) {
        ImagesActivity.currentPosition = pos;

        View sharedImageView = root.findViewById(R.id.image);
        String transitionName = ViewCompat.getTransitionName(sharedImageView);

        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra(ImageActivity.BREED_EXTRA_KEY, (subBreed == null ? breed : subBreed));
        intent.putExtra(ImageActivity.IMAGE_EXTRA_KEY, item);
        intent.putStringArrayListExtra(ImageActivity.IMAGE_LIST_EXTRA_KEY, (ArrayList<String>) images);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, sharedImageView, transitionName);

        startActivity(intent, options.toBundle());
    }
}
