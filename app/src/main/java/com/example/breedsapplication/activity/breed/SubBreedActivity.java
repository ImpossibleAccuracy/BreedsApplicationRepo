package com.example.breedsapplication.activity.breed;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.core.view.WindowCompat;

import com.example.breedsapplication.R;
import com.example.breedsapplication.activity.AppActivity;
import com.example.breedsapplication.activity.image.ImagesActivity;
import com.example.breedsapplication.databinding.ActivitySubBreedBinding;
import com.example.breedsapplication.fragment.sub_breed.SubBreedFragment;
import com.example.breedsapplication.model.Breed;

public class SubBreedActivity extends AppActivity implements SubBreedFragment.OnItemSelected {
    public static final String BREED_EXTRA_KEY = "BreedKey";

    private ActivitySubBreedBinding binding;

    private Breed breed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubBreedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setTitle(this.breed.getName());
        setupToolbar(binding.toolbar);

        if (savedInstanceState == null) {
            SubBreedFragment fragment = SubBreedFragment.newInstance(breed);
            fragment.setOnItemSelected(this);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow();
        }
    }

    @Override
    protected void parseIntent(Intent intent) {
        this.breed = (Breed) intent.getSerializableExtra(BREED_EXTRA_KEY);
    }

    @Override
    public void onItemSelected(int pos, String item, View root) {
        Intent intent = new Intent(this, ImagesActivity.class);
        intent.putExtra(ImagesActivity.BREED_EXTRA_KEY, breed.getName());
        intent.putExtra(ImagesActivity.SUB_BREED_EXTRA_KEY, item);

        startActivity(intent);
    }
}