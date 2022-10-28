package com.example.breedsapplication.activity.image.single;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.example.breedsapplication.R;
import com.example.breedsapplication.databinding.ActivityImageBinding;
import com.example.breedsapplication.fragment.image.single.ImageFragment;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    public static final String BREED_EXTRA_KEY = "BreedKey";
    public static final String IMAGE_EXTRA_KEY = "ImageKey";
    public static final String IMAGE_LIST_EXTRA_KEY = "ImageListKey";

    private ActivityImageBinding binding;

    private String image;
    private String breed;
    private ArrayList<String> images;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(
                getWindow(), false);

        binding = ActivityImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        this.image = intent.getStringExtra(IMAGE_EXTRA_KEY);
        this.breed = intent.getStringExtra(BREED_EXTRA_KEY);
        this.images = intent.getStringArrayListExtra(IMAGE_LIST_EXTRA_KEY);

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(this.breed);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        if (savedInstanceState == null) {
            ImageFragment fragment = ImageFragment.newInstance(image, images);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
