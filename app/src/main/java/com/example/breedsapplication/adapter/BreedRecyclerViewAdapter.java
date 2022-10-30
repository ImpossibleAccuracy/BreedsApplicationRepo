package com.example.breedsapplication.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.breedsapplication.R;
import com.example.breedsapplication.adapter.listener.OnBreedSelected;
import com.example.breedsapplication.databinding.ItemBreedBinding;
import com.example.breedsapplication.adapter.holder.BreedViewHolder;
import com.example.breedsapplication.model.Breed;

import java.util.List;

public class BreedRecyclerViewAdapter extends RecyclerAdapterWithContext<BreedViewHolder> {
    private List<Breed> breeds;
    private OnBreedSelected onBreedSelected;

    public BreedRecyclerViewAdapter(Context context) {
        super(context);
    }

    public BreedRecyclerViewAdapter(Context context, List<Breed> breeds) {
        super(context);
        this.breeds = breeds;
    }

    public List<Breed> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<Breed> breeds) {
        this.breeds = breeds;
    }

    public void setOnItemSelectedListener(OnBreedSelected onBreedSelected) {
        this.onBreedSelected = onBreedSelected;
    }

    @NonNull
    @Override
    public BreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBreedBinding binding = ItemBreedBinding
                .inflate(getLayoutInflater(), parent, false);
        return new BreedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BreedViewHolder holder, int position) {
        Breed breed = breeds.get(position);
        holder.setBreed(breed);

        ItemBreedBinding binding = holder.getBinding();
        binding.title.setText(breed.getName());

        if (breed.getSubBreeds().isEmpty()) {
            binding.subBreeds.setText(R.string.no_sub_breeds);
        } else {
            binding.subBreeds.setText(
                    getContext().getString(
                            R.string.sub_breeds,
                            breed.getSubBreeds().size()));
        }

        binding.getRoot().setOnClickListener(
                v -> {
                    if (onBreedSelected != null) {
                        onBreedSelected.onBreedSelected(position, breed, v);
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (breeds == null)
            return 0;
        return breeds.size();
    }

}
