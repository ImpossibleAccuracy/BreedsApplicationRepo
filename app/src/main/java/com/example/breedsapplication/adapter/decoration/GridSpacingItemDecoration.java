package com.example.breedsapplication.adapter.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int colCount;
    private final int spacing;
    private final boolean includeEdge;

    public GridSpacingItemDecoration(int colCount, int spacing, boolean includeEdge) {
        this.colCount = colCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % colCount;

        if (includeEdge) {
            outRect.left = spacing - column * spacing / colCount;
            outRect.right = (column + 1) * spacing / colCount;

            if (position < colCount) {
                outRect.top = spacing;
            }
            outRect.bottom = spacing;
        } else {
            outRect.left = column * spacing / colCount;
            outRect.right = spacing - (column + 1) * spacing / colCount;
            if (position >= colCount) {
                outRect.top = spacing;
            }
        }
    }
}
