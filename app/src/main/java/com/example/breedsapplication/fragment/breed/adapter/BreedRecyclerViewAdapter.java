package com.example.breedsapplication.fragment.breed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breedsapplication.R;
import com.example.breedsapplication.databinding.ItemBreedBinding;
import com.example.breedsapplication.model.Breed;

import java.util.List;

public class BreedRecyclerViewAdapter extends RecyclerView.Adapter<BreedViewHolder> {
    private final LayoutInflater inflater;
    private List<Breed> breeds;
    private OnItemSelected onItemSelected;

    public BreedRecyclerViewAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public BreedRecyclerViewAdapter(Context context, List<Breed> breeds) {
        this(context);
        this.breeds = breeds;
    }

    public Context getContext() {
        return inflater.getContext();
    }

    public List<Breed> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<Breed> breeds) {
        this.breeds = breeds;
    }

    public void setOnItemSelectedListener(OnItemSelected onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    @NonNull
    @Override
    public BreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBreedBinding binding = ItemBreedBinding
                .inflate(inflater, parent, false);
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
                    if (onItemSelected != null) {
                        onItemSelected.onItemSelected(position, breed, v);
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (breeds == null)
            return 0;
        return breeds.size();
    }

    public interface OnItemSelected {
        void onItemSelected(int pos, Breed item, View root);
    }
}
