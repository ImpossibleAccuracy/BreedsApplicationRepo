package com.example.breedsapplication.adapter.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breedsapplication.R;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "DividerItem";
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private final Rect mBounds;
    private final Context mContext;
    private final Drawable mDrawable;

    public DividerItemDecoration(Context context) {
        mContext = context;
        mBounds = new Rect();

        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDrawable = a.getDrawable(0);
        if (mDrawable == null) {
            Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this DividerItemDecoration. ");
        }

        a.recycle();
    }

    @Override
    public void onDraw(@NonNull Canvas c,
                       RecyclerView parent,
                       @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() != null && mDrawable != null) {
            drawVertical(c, parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        int left = (int) mContext.getResources().getDimension(R.dimen.divider_margins);
        int right = left;

        if (parent.getClipToPadding()) {
            left += parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight() - right;
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            right = parent.getWidth() - right;
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(canvas);
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        if (mDrawable == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }

        outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
    }
}

