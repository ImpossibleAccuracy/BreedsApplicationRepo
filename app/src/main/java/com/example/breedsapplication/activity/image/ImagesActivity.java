package com.example.breedsapplication.activity.image;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.FragmentManager;

import com.example.breedsapplication.R;
import com.example.breedsapplication.databinding.ActivityImagesBinding;
import com.example.breedsapplication.fragment.image.list.ImagesFragment;

public class ImagesActivity extends AppCompatActivity {
    public static final String BREED_EXTRA_KEY = "BreedKey";
    public static final String SUB_BREED_EXTRA_KEY = "SubBreedKey";

    public static int currentPosition;

    private ActivityImagesBinding binding;

    private String breed;
    private String subBreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(
                getWindow(), false);

        ImagesActivity.currentPosition = 0;

        binding = ActivityImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        this.breed = intent.getStringExtra(BREED_EXTRA_KEY);
        this.subBreed = intent.getStringExtra(SUB_BREED_EXTRA_KEY);

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(this.breed);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        if (savedInstanceState == null) {
            ImagesFragment fragment = ImagesFragment.newInstance(breed, subBreed);

            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.container, fragment, ImagesFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
