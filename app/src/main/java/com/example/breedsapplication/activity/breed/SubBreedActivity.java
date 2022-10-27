package com.example.breedsapplication.activity.breed;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;

import com.example.breedsapplication.R;
import com.example.breedsapplication.activity.image.list.ImagesActivity;
import com.example.breedsapplication.databinding.ActivitySubBreedBinding;
import com.example.breedsapplication.fragment.image.list.ImagesFragment;
import com.example.breedsapplication.fragment.sub_breed.SubBreedFragment;
import com.example.breedsapplication.model.Breed;

public class SubBreedActivity extends AppCompatActivity implements SubBreedFragment.OnItemSelected {
    private ActivitySubBreedBinding binding;

    private Breed breed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(
                getWindow(), false);

        binding = ActivitySubBreedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        this.breed = (Breed) intent.getSerializableExtra(Breed.class.getSimpleName());

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(this.breed.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        if (savedInstanceState == null) {
            SubBreedFragment fragment = SubBreedFragment.newInstance(breed);
            fragment.setOnItemSelected(this);

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

    @Override
    public void onItemSelected(int pos, String item, View root) {
        Intent intent = new Intent(this, ImagesActivity.class);
        intent.putExtra(ImagesActivity.BREED_EXTRA_KEY, breed.getName());
        intent.putExtra(ImagesActivity.SUB_BREED_EXTRA_KEY, item);

        startActivity(intent);
    }
}