package com.example.breedsapplication.fragment.breed;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.breedsapplication.adapter.BreedRecyclerViewAdapter;
import com.example.breedsapplication.adapter.decoration.DividerItemDecoration;
import com.example.breedsapplication.databinding.FragmentBreedBinding;
import com.example.breedsapplication.model.Breed;

import java.util.Comparator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BreedFragment extends Fragment {
    private BreedViewModel viewModel;
    private FragmentBreedBinding binding;
    private BreedRecyclerViewAdapter adapter;

    private BreedRecyclerViewAdapter.OnItemSelected onItemSelected;

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
        final Context context = requireContext();

        adapter = new BreedRecyclerViewAdapter(context);
        // TODO: Fragment should notify the activity that item has been selected.
        adapter.setOnItemSelectedListener(onItemSelected);
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
                new LinearLayoutManager(context));
        binding.recyclerView.addItemDecoration(
                new DividerItemDecoration(context));

        return binding.getRoot();
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

    private void sortBreeds(Comparator<Breed> comparator) {
        // TODO: remove on release ver
        viewModel.sortBreeds(comparator);
        mHandler.post(adapter::notifyDataSetChanged);
    }

    public void setOnItemSelected(BreedRecyclerViewAdapter.OnItemSelected onItemSelected) {
        this.onItemSelected = onItemSelected;
        if (adapter != null) {
            adapter.setOnItemSelectedListener(onItemSelected);
        }
    }
}