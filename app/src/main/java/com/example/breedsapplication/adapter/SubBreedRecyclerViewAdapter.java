package com.example.breedsapplication.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.breedsapplication.adapter.holder.SubBreedViewHolder;
import com.example.breedsapplication.adapter.listener.OnSubBreedSelected;
import com.example.breedsapplication.databinding.ItemSubBreedBinding;
import com.example.breedsapplication.model.Breed;

public class SubBreedRecyclerViewAdapter extends RecyclerAdapterWithContext<SubBreedViewHolder> {
    private Breed breed;
    private OnSubBreedSelected onSubBreedSelected;

    public SubBreedRecyclerViewAdapter(Context context) {
        super(context);
    }

    public SubBreedRecyclerViewAdapter(Context context, Breed breed) {
        this(context);
        this.breed = breed;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public void setOnItemSelectedListener(OnSubBreedSelected onSubBreedSelected) {
        this.onSubBreedSelected = onSubBreedSelected;
    }

    @NonNull
    @Override
    public SubBreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSubBreedBinding binding = ItemSubBreedBinding
                .inflate(getLayoutInflater(), parent, false);
        return new SubBreedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubBreedViewHolder holder, int position) {
        String sb = breed.getSubBreeds().get(position);

        ItemSubBreedBinding binding = holder.getBinding();
        binding.title.setText(sb);

        binding.getRoot().setOnClickListener(
                v -> {
                    if (onSubBreedSelected != null) {
                        onSubBreedSelected.onSubBreedSelected(position, sb, v);
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
