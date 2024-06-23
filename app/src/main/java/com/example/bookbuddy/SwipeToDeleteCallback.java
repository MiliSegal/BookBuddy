// SwipeToDeleteCallback.java
package com.example.bookbuddy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private BookAdapter mAdapter;
    private Paint mPaint;

    public SwipeToDeleteCallback(BookAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(48);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.deleteItem(position);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX > 0) { // Swiping to the right
                c.drawText("Deleting", viewHolder.itemView.getLeft() + 50, viewHolder.itemView.getTop() + 100, mPaint);
            } else { // Swiping to the left
                c.drawText("Deleting", viewHolder.itemView.getRight() - 200, viewHolder.itemView.getTop() + 100, mPaint);
            }
        }
    }
}
