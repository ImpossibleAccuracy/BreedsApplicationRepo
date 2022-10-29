package com.example.breedsapplication.activity.image;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import com.example.breedsapplication.R;
import com.example.breedsapplication.activity.AppActivity;
import com.example.breedsapplication.adapter.ImageRecyclerViewAdapter;
import com.example.breedsapplication.databinding.ActivityImagesBinding;
import com.example.breedsapplication.fragment.image.list.ImagesFragment;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppActivity implements ImageRecyclerViewAdapter.OnItemSelected {
    public static final String BREED_EXTRA_KEY = "BreedKey";
    public static final String SUB_BREED_EXTRA_KEY = "SubBreedKey";

    public static int currentPosition;

    private ActivityImagesBinding binding;

    private String breed;
    private String subBreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImagesActivity.currentPosition = 0;

        binding = ActivityImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setTitle(this.breed);
        setupToolbar(binding.toolbar);

        if (savedInstanceState == null) {
            ImagesFragment fragment = ImagesFragment.newInstance(breed, subBreed);
            fragment.setOnItemSelected(this);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, ImagesFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    protected void parseIntent(Intent intent) {
        this.breed = intent.getStringExtra(BREED_EXTRA_KEY);
        this.subBreed = intent.getStringExtra(SUB_BREED_EXTRA_KEY);
    }

    @Override
    public void onItemSelected(int pos, String item, List<String> images, View root) {
        ImagesActivity.currentPosition = pos;

        View sharedImageView = root.findViewById(R.id.image);
        String transitionName = ViewCompat.getTransitionName(sharedImageView);

        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra(ImageActivity.IMAGE_EXTRA_KEY, item);
        intent.putStringArrayListExtra(ImageActivity.IMAGE_LIST_EXTRA_KEY, (ArrayList<String>) images);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, sharedImageView, transitionName);

        startActivity(intent, options.toBundle());
    }
}
