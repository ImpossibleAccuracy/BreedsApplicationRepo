package com.example.breedsapplication.activity.image;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.breedsapplication.R;
import com.example.breedsapplication.activity.AppActivity;
import com.example.breedsapplication.databinding.ActivityImageBinding;
import com.example.breedsapplication.fragment.image.single.ImageFragment;

import java.util.ArrayList;

public class ImageActivity extends AppActivity {
    public static final String IMAGE_EXTRA_KEY = "Image";
    public static final String IMAGE_LIST_EXTRA_KEY = "ImageList";

    private ActivityImageBinding binding;

    private String image;
    private ArrayList<String> imageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        postponeEnterTransition();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, ImageFragment.newInstance(image, imageList))
                    .commit();
        }
    }

    @Override
    protected void parseIntent(Intent intent) {
        this.image = intent.getStringExtra(IMAGE_EXTRA_KEY);
        this.imageList = intent.getStringArrayListExtra(IMAGE_LIST_EXTRA_KEY);
    }
}
