package com.example.breedsapplication.fragment.sub_breed;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.breedsapplication.adapter.SubBreedRecyclerViewAdapter;
import com.example.breedsapplication.adapter.decoration.DividerItemDecoration;
import com.example.breedsapplication.adapter.listener.OnSubBreedSelected;
import com.example.breedsapplication.databinding.FragmentSubBreedBinding;
import com.example.breedsapplication.model.Breed;

public class SubBreedFragment extends Fragment {
    private FragmentSubBreedBinding binding;
    private SubBreedRecyclerViewAdapter adapter;

    private Breed breed;
    private OnSubBreedSelected onSubBreedSelected;

    public static SubBreedFragment newInstance(Breed breed) {
        SubBreedFragment subBreedFragment = new SubBreedFragment();

        Bundle args = new Bundle();
        args.putSerializable(Breed.class.getSimpleName(), breed);

        subBreedFragment.setArguments(args);
        return subBreedFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args =
                (savedInstanceState == null ? getArguments() : savedInstanceState);
        if (args != null) {
            breed = (Breed) args.getSerializable(Breed.class.getSimpleName());
        }

        adapter = new SubBreedRecyclerViewAdapter(getContext(), breed);
        adapter.setOnItemSelectedListener(onSubBreedSelected);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(Breed.class.getSimpleName(), breed);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSubBreedBinding.inflate(inflater);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));
        binding.recyclerView.addItemDecoration(
                new DividerItemDecoration(requireContext()));

        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setBreed(Breed breed) {
        this.breed = breed;
        if (adapter != null) {
            adapter.setBreed(breed);
            adapter.notifyDataSetChanged();
        }
    }

    public void setOnItemSelected(OnSubBreedSelected onSubBreedSelected) {
        this.onSubBreedSelected = onSubBreedSelected;
        if (adapter != null) {
            adapter.setOnItemSelectedListener(onSubBreedSelected);
        }
    }

}