package com.webxert.imagefilter.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hp on 8/6/2018.
 */

public class SpaceItemDecorator extends RecyclerView.ItemDecoration {
    int space;

    public SpaceItemDecorator(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
            outRect.left = space;
            outRect.right = 0;
        } else {
            outRect.left = 0;
            outRect.right = space;
        }
    }
}
