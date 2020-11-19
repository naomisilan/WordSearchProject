package com.example.joshvocal.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.example.joshvocal.model.BoardPosition;
import com.example.joshvocal.model.WordSearch;

abstract public class OnItemTouchMultiDragListener implements RecyclerView.OnItemTouchListener {

    private String mTag;

    private int mSelectionCount;
    private boolean isInBounds;
    private BoardPosition mStartBoardPosition;
    private BoardPosition mLastBoardPosition;
    private BoardPosition mEndBoardPosition;

    private boolean isFirstItemTouched;
    private Context mContext;

    private WordSearch mWordSearch;

    public OnItemTouchMultiDragListener(String tag, Context context, WordSearch wordSearch) {
        mTag = tag;
        mContext = context;
        mWordSearch = wordSearch;
    }

    public void onDragSelectionUp(BoardPosition startPos, BoardPosition endPos, BoardPosition lastPos,
                                  RecyclerView.ViewHolder holder) {
    }

    abstract public void onTouchFirstSelection(BoardPosition boardPos, int pos);

    abstract public void onMoveSelection(BoardPosition boardPos, int pos);

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

        if (motionEvent.getPointerCount() > 1) {
            // Touched with multiple fingers.
            return false;
        }

        int action = motionEvent.getAction();

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP ||
                action == MotionEvent.ACTION_CANCEL) {

            View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            RecyclerView.ViewHolder holder = null;
            mEndBoardPosition = null;

            if (view != null) {

                View vTouch = view.findViewWithTag(mTag);

                if (vTouch != null) {
                    holder = recyclerView.getChildViewHolder(view);
                    if (holder.getAdapterPosition() >= 0) {
                        mEndBoardPosition = mWordSearch.getBoard().getBoardPosition(holder.getAdapterPosition());
                    }
                }
            }

            onDragSelectionUp(mStartBoardPosition, mEndBoardPosition, mLastBoardPosition, holder);

            // Reset variables on drag up
            isInBounds = false;
            isFirstItemTouched = false;
            mSelectionCount = 0;

            return false;
        }

        View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

        if (view != null) {

            View vTouch = view.findViewWithTag(mTag);

            if (vTouch != null) {

                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                BoardPosition currentBoardPosition = null;

                if (viewHolder.getAdapterPosition() >= 0) {
                    currentBoardPosition = mWordSearch.getBoard().getBoardPosition(viewHolder.getAdapterPosition());
                }

                if (isInBounds && currentBoardPosition == mLastBoardPosition || currentBoardPosition == null) {
                    return false;
                }

                isInBounds = true;

                if (action == MotionEvent.ACTION_DOWN && !currentBoardPosition.isWordFound()) {
                    onTouchFirstSelection(currentBoardPosition, viewHolder.getAdapterPosition());
                    isFirstItemTouched = true;
                } else if (isFirstItemTouched && action == MotionEvent.ACTION_MOVE && !currentBoardPosition.isWordFound()) {
                    onMoveSelection(currentBoardPosition, viewHolder.getAdapterPosition());
                }

                if (mSelectionCount == 0) {
                    mStartBoardPosition = currentBoardPosition;
                }

                mLastBoardPosition = currentBoardPosition;
                mSelectionCount++;
            } else {
                isInBounds = false;
            }
        } else {
            isInBounds = false;
        }

        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        // Empty generated method
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {
        // Empty generated method
    }
}
