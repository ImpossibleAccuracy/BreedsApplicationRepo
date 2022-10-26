package com.example.breedsapplication.fragment.breed;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.breedsapplication.R;
import com.example.breedsapplication.activity.breed.SubBreedActivity;
import com.example.breedsapplication.activity.image.ImageActivity;
import com.example.breedsapplication.databinding.FragmentBreedBinding;
import com.example.breedsapplication.fragment.breed.adapter.BreedRecyclerViewAdapter;
import com.example.breedsapplication.model.Breed;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Comparator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BreedFragment extends Fragment implements MenuProvider {
    private BreedViewModel viewModel;
    private FragmentBreedBinding binding;
    private BreedRecyclerViewAdapter adapter;

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this)
                .get(BreedViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBreedBinding.inflate(inflater, container, false);

        adapter = new BreedRecyclerViewAdapter(getContext());
        // TODO: Fragment should notify the activity that item has been selected.
        adapter.setOnItemSelectedListener(this::onBreedSelected);
        viewModel.getBreeds().observe(
                getViewLifecycleOwner(), b -> {
                    if (b != null) {
                        adapter.setBreeds(b);
                        // adapter.notifyItemRangeInserted(0, b.size());
                        // TODO: remove sort
                        sortBreeds(new Breed.SubListSizeComparator().reversed());
                    }
                });

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (viewModel.getBreeds().getValue() == null) {
            executor.execute(viewModel::loadBreeds);
        }

        requireActivity().addMenuProvider(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        requireActivity().removeMenuProvider(this);
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_sort) {
            showSortDialog();
            return true;
        }
        return false;
    }

    private void showSortDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle(R.string.sort_dialog_title);
        builder.setItems(R.array.sort_variants, (dialogInterface, i) -> {
            Comparator<Breed> comparator;
            switch (i) {
                case 0:
                    comparator = new Breed.TitleComparator();
                    break;
                case 1:
                    comparator = new Breed.SubListSizeComparator().reversed();
                    break;
                default:
                    throw new NullPointerException("Unknown sort type.");
            }

            executor.execute(() -> sortBreeds(comparator));
        });

        builder.show();
    }

    private void sortBreeds(Comparator<Breed> comparator) {
        viewModel.sortBreeds(comparator);
        mHandler.post(adapter::notifyDataSetChanged);
    }

    private void onBreedSelected(int pos, Breed breed, View v) {
        Intent intent;
        if (breed.getSubBreeds() == null ||
                breed.getSubBreeds().size() == 0 ||
                breed.getSubBreeds().isEmpty()) {
            intent = new Intent(getActivity(), ImageActivity.class);
            intent.putExtra(Breed.class.getSimpleName(), breed.getName());
        } else {
            intent = new Intent(getActivity(), SubBreedActivity.class);
            intent.putExtra(Breed.class.getSimpleName(), breed);
        }
        startActivity(intent);
    }
}