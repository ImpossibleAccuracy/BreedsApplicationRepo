package com.example.breedsapplication.activity.image.list;

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
import com.example.breedsapplication.activity.image.single.ImageActivity;
import com.example.breedsapplication.databinding.ActivityImagesBinding;
import com.example.breedsapplication.fragment.image.list.ImagesFragment;
import com.example.breedsapplication.fragment.image.list.adapter.ImageRecyclerViewAdapter;

public class ImagesActivity extends AppCompatActivity implements ImageRecyclerViewAdapter.OnItemSelected {
    public static final String BREED_EXTRA_KEY = "BreedKey";
    public static final String SUB_BREED_EXTRA_KEY = "SubBreedKey";

    private ActivityImagesBinding binding;

    private String breed;
    private String subBreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(
                getWindow(), false);

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
        View sharedImageView = root.findViewById(R.id.image);
        String transitionName = ViewCompat.getTransitionName(sharedImageView);

        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra(ImageActivity.IMAGE_EXTRA_KEY, item);
        intent.putExtra(ImageActivity.BREED_EXTRA_KEY, breed);
        intent.putExtra(ImageActivity.TRANSITION_EXTRA_KEY, transitionName);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                sharedImageView,
                transitionName);

        startActivity(intent, options.toBundle());
    }
}
