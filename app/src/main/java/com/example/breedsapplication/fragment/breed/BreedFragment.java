package com.example.breedsapplication.fragment.breed;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.breedsapplication.adapter.BreedRecyclerViewAdapter;
import com.example.breedsapplication.adapter.decoration.DividerItemDecoration;
import com.example.breedsapplication.adapter.listener.OnBreedSelected;
import com.example.breedsapplication.databinding.FragmentBreedBinding;
import com.example.breedsapplication.model.Breed;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BreedFragment extends Fragment {
    private BreedViewModel viewModel;
    private FragmentBreedBinding binding;
    private BreedRecyclerViewAdapter adapter;

    private OnBreedSelected onBreedSelected;

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

        setupAdapter();
        setupList();

        return binding.getRoot();
    }

    private void setupAdapter() {
        adapter = new BreedRecyclerViewAdapter(requireContext());
        adapter.setOnItemSelectedListener(onBreedSelected);
        viewModel.getBreeds().observe(
                getViewLifecycleOwner(),
                b -> {
                    if (b != null) {
                        adapter.setBreeds(b);
                        adapter.notifyItemRangeInserted(0, b.size());
                    }
                });
    }

    private void setupList() {
        final Context context = requireContext();

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(
                new LinearLayoutManager(context));
        binding.recyclerView.addItemDecoration(
                new DividerItemDecoration(context));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (viewModel.getBreeds().getValue() == null) {
            executor.execute(viewModel::loadBreeds);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public LiveData<List<Breed>> getBreeds() {
        return viewModel.getBreeds();
    }

    public void setOnItemSelected(OnBreedSelected onBreedSelected) {
        this.onBreedSelected = onBreedSelected;
        if (adapter != null) {
            adapter.setOnItemSelectedListener(onBreedSelected);
        }
    }
}