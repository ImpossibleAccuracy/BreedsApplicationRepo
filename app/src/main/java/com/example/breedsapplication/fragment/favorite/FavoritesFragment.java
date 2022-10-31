package com.example.breedsapplication.fragment.favorite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.breedsapplication.activity.image.ImagesActivity;
import com.example.breedsapplication.adapter.FavoriteRecyclerViewAdapter;
import com.example.breedsapplication.databinding.FragmentFavoritesBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment implements FavoriteRecyclerViewAdapter.OnImageSelected {
    private FavoritesViewModel viewModel;
    private FragmentFavoritesBinding binding;
    private FavoriteRecyclerViewAdapter adapter;

    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this)
                .get(FavoritesViewModel.class);
    }

    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        final Context context = requireContext();

        adapter = new FavoriteRecyclerViewAdapter(context);
        adapter.setOnImageSelected(this);
        viewModel.getFavorites().observe(
                getViewLifecycleOwner(),
                f -> {
                    if (f != null) {
                        adapter.setFavorites(f);
                        adapter.notifyDataSetChanged();
                    }
                });

        binding.favorites.setAdapter(adapter);
        binding.favorites.setLayoutManager(new LinearLayoutManager(context));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (viewModel.getFavorites().getValue() == null) {
            loadFavorites();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadFavorites() {
        executor.execute(viewModel::loadFavorites);
    }

    @Override
    public void onImageSelected(int pos, String breed, List<String> images, View view) {
        Intent intent = new Intent(requireActivity(), ImagesActivity.class);
        intent.putExtra(ImagesActivity.BREED_EXTRA_KEY, breed);
        intent.putStringArrayListExtra(
                ImagesActivity.IMAGES_EXTRA_KEY, new ArrayList<>(images));

        startActivity(intent);
    }
}