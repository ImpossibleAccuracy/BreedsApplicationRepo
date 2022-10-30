package com.example.breedsapplication.activity.breed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.breedsapplication.R;
import com.example.breedsapplication.activity.AppActivity;
import com.example.breedsapplication.activity.image.ImagesActivity;
import com.example.breedsapplication.adapter.listener.OnSubBreedSelected;
import com.example.breedsapplication.databinding.ActivitySubBreedBinding;
import com.example.breedsapplication.fragment.sub_breed.SubBreedFragment;
import com.example.breedsapplication.model.Breed;

public class SubBreedActivity extends AppActivity implements OnSubBreedSelected {
    public static final String BREED_EXTRA_KEY = "BreedKey";

    private ActivitySubBreedBinding binding;
    private SubBreedFragment subBreedFragment;

    private Breed breed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubBreedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar(this.breed.getName());

        if (savedInstanceState == null) {
            subBreedFragment = SubBreedFragment.newInstance(breed);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, subBreedFragment)
                    .commitNow();
        } else {
            subBreedFragment = (SubBreedFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.container);

            if (subBreedFragment == null)
                throw new NullPointerException("Fragment not found.");
        }
        subBreedFragment.setOnItemSelected(this);
    }

    @Override
    protected void parseIntent(Intent intent) {
        this.breed = (Breed) intent.getSerializableExtra(BREED_EXTRA_KEY);
    }

    @Override
    public void onSubBreedSelected(int pos, String item, View root) {
        Intent intent = new Intent(this, ImagesActivity.class);
        intent.putExtra(ImagesActivity.BREED_EXTRA_KEY, breed.getName());
        intent.putExtra(ImagesActivity.SUB_BREED_EXTRA_KEY, item);

        startActivity(intent);
    }
}