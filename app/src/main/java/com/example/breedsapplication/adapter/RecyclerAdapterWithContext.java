package com.example.breedsapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerAdapterWithContext<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    private final Context context;
    private final LayoutInflater inflater;

    public RecyclerAdapterWithContext(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return context;
    }

    public LayoutInflater getLayoutInflater() {
        return inflater;
    }
}
