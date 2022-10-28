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

import com.example.breedsapplication.adapter.holder.SubBreedRecyclerViewAdapter;
import com.example.breedsapplication.databinding.FragmentSubBreedBinding;
import com.example.breedsapplication.model.Breed;

public class SubBreedFragment extends Fragment {
    private FragmentSubBreedBinding binding;
    private SubBreedRecyclerViewAdapter adapter;

    private Breed breed;
    private OnItemSelected onItemSelected;

    public static SubBreedFragment newInstance(Breed breed) {
        SubBreedFragment subBreedFragment = new SubBreedFragment();

        Bundle args = new Bundle();
        args.putSerializable(Breed.class.getSimpleName(), breed);

        subBreedFragment.setArguments(args);
        return subBreedFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle state) {
        super.onCreate(state);

        Bundle args = getArguments();
        if (args != null) {
            breed = (Breed) args.getSerializable(Breed.class.getSimpleName());
        }

        adapter = new SubBreedRecyclerViewAdapter(getContext(), breed);
        adapter.setOnItemSelectedListener(onItemSelected);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setBreed(Breed breed) {
        this.breed = breed;
        if (adapter != null) {
            adapter.setBreed(breed);
            adapter.notifyDataSetChanged();
        }
    }

    public void setOnItemSelected(OnItemSelected onItemSelected) {
        this.onItemSelected = onItemSelected;
        if (adapter != null) {
            adapter.setOnItemSelectedListener(onItemSelected);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSubBreedBinding.inflate(inflater);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    public interface OnItemSelected {
        void onItemSelected(int pos, String item, View root);
    }
}