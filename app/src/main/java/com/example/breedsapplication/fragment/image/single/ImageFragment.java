package com.example.breedsapplication.fragment.image.single;

import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.breedsapplication.R;
import com.example.breedsapplication.activity.image.ImagesActivity;
import com.example.breedsapplication.adapter.ImageListAdapter;
import com.example.breedsapplication.databinding.FragmentImageBinding;
import com.example.breedsapplication.service.FavoriteService;
import com.example.breedsapplication.service.FavoriteServiceDBImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImageFragment extends Fragment implements View.OnClickListener {
    private static final String BREED_EXTRA_KEY = "Breed";
    private static final String IMAGE_EXTRA_KEY = "Image";
    private static final String IMAGE_LIST_EXTRA_KEY = "ImageList";

    private ImageListAdapter adapter;
    private FragmentImageBinding binding;
    private FavoriteService favoriteService;

    private String breed;
    private String image;
    private List<String> images;
    private boolean isFavorite;

    public static ImageFragment newInstance(String breed, String image, ArrayList<String> images) {
        ImageFragment fragment = new ImageFragment();

        Bundle args = new Bundle();
        args.putString(BREED_EXTRA_KEY, breed);
        args.putString(IMAGE_EXTRA_KEY, image);
        args.putStringArrayList(IMAGE_LIST_EXTRA_KEY, images);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            breed = args.getString(BREED_EXTRA_KEY);
            image = args.getString(IMAGE_EXTRA_KEY);
            images = args.getStringArrayList(IMAGE_LIST_EXTRA_KEY);
        }

        favoriteService = new FavoriteServiceDBImpl(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentImageBinding.inflate(inflater);

        binding.fab.setOnClickListener(this);

        adapter = new ImageListAdapter(this);
        adapter.setImages(images);

        binding.pager.setAdapter(adapter);
        binding.pager.setCurrentItem(ImagesActivity.currentPosition, false);
        binding.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                ImagesActivity.currentPosition = position;
                updateUI();
            }
        });

        prepareSharedElementTransition();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            if (isFavorite) {
                favoriteService.remove(images.get(ImagesActivity.currentPosition));
            } else {
                favoriteService.insert(breed, images.get(ImagesActivity.currentPosition));
            }
            isFavorite = !isFavorite;
            updateFab(isFavorite);
        }
    }

    private void updateUI() {
        isFavorite = favoriteService.isFavorite(
                images.get(ImagesActivity.currentPosition));
        updateFab(isFavorite);
    }

    private void updateFab(boolean isFavorite) {
        @DrawableRes int res;
        if (isFavorite) {
            res = R.drawable.ic_baseline_favorite_24;
        } else {
            res = R.drawable.ic_baseline_favorite_border_24;
        }

        binding.fab.setImageResource(res);
    }

    private void prepareSharedElementTransition() {
        Transition transition =
                TransitionInflater.from(getContext())
                        .inflateTransition(R.transition.image_shared_element_transition);
        setSharedElementEnterTransition(transition);

        FragmentActivity activity = requireActivity();
        activity.setEnterSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        FragmentManager manager = getChildFragmentManager();
                        Fragment currentFragment = manager.findFragmentByTag(
                                String.format("f%s", ImagesActivity.currentPosition));
                        View view = currentFragment.getView();
                        if (view == null) {
                            return;
                        }

                        sharedElements.put(names.get(0), view.findViewById(R.id.image));
                    }
                });
    }
}
