package com.example.breedsapplication.fragment.sub_breed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breedsapplication.databinding.ItemSubBreedBinding;
import com.example.breedsapplication.fragment.sub_breed.SubBreedFragment;
import com.example.breedsapplication.model.Breed;

public class SubBreedRecyclerViewAdapter extends RecyclerView.Adapter<SubBreedViewHolder> {
    private final LayoutInflater inflater;

    private Breed breed;
    private SubBreedFragment.OnItemSelected onItemSelected;

    public SubBreedRecyclerViewAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public SubBreedRecyclerViewAdapter(Context context, Breed breed) {
        this(context);
        this.breed = breed;
    }

    public Context getContext() {
        return inflater.getContext();
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public void setOnItemSelectedListener(SubBreedFragment.OnItemSelected onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    @NonNull
    @Override
    public SubBreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSubBreedBinding binding = ItemSubBreedBinding
                .inflate(inflater, parent, false);
        return new SubBreedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubBreedViewHolder holder, int position) {
        String sb = breed.getSubBreeds().get(position);

        ItemSubBreedBinding binding = holder.getBinding();
        binding.title.setText(sb);

        binding.getRoot().setOnClickListener(
                v -> {
                    if (onItemSelected != null) {
                        onItemSelected.onItemSelected(position, sb, v);
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (breed == null || breed.getSubBreeds() == null)
            return 0;
        return breed.getSubBreeds().size();
    }
}
