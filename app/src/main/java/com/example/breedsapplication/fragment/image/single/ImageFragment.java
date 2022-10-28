package com.example.breedsapplication.fragment.image.single;

import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.breedsapplication.R;
import com.example.breedsapplication.activity.image.ImagesActivity;
import com.example.breedsapplication.adapter.ImageListAdapter;
import com.example.breedsapplication.databinding.FragmentImageBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImageFragment extends Fragment {
    private static final String IMAGE_EXTRA_KEY = "Image";
    private static final String IMAGE_LIST_EXTRA_KEY = "ImageList";

    private ImageListAdapter adapter;
    private FragmentImageBinding binding;

    private String image;
    private List<String> images;

    public static ImageFragment newInstance(String image, ArrayList<String> images) {
        ImageFragment fragment = new ImageFragment();

        Bundle args = new Bundle();
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
            image = args.getString(IMAGE_EXTRA_KEY);
            images = args.getStringArrayList(IMAGE_LIST_EXTRA_KEY);
        }

        getActivity().setTheme(R.style.Theme_BreedsApplication_ImageFullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentImageBinding.inflate(inflater);

        adapter = new ImageListAdapter(this);
        adapter.setImages(images);

        binding.pager.setAdapter(adapter);
        binding.pager.setCurrentItem(ImagesActivity.currentPosition, false);
        binding.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                ImagesActivity.currentPosition = position;
            }
        });

        prepareSharedElementTransition();
        if (savedInstanceState == null) {
            postponeEnterTransition();
        }

        return binding.getRoot();
    }

    private void prepareSharedElementTransition() {
        Transition transition =
                TransitionInflater.from(getContext())
                        .inflateTransition(R.transition.image_shared_element_transition);
        setSharedElementEnterTransition(transition);

        setEnterSharedElementCallback(
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
