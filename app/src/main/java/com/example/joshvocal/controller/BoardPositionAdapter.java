package com.example.joshvocal.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.example.joshvocal.R;
import com.example.joshvocal.model.BoardPosition;

import java.util.List;

public class BoardPositionAdapter extends RecyclerView.Adapter<BoardPositionAdapter.BoardPositionHolder> {

    private Context mContext;
    private List<BoardPosition> mList;

    public BoardPositionAdapter(Context context, List<BoardPosition> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public BoardPositionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_board_position, viewGroup, false);
        return new BoardPositionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardPositionHolder boardPositionHolder, int position) {
        BoardPosition boardPosition = mList.get(position);

        boardPositionHolder.mCharacterTextView.setText(Character.toString(boardPosition.getCharacter()));

        if (boardPosition.getIsSelected()) {
            boardPositionHolder.mCharacterTextView.getBackground().setTint(ContextCompat.getColor(mContext, R.color.purple));
            setScaleAnimation(boardPositionHolder.itemView);
        } else {
            boardPositionHolder.mCharacterTextView.getBackground().setTint(ContextCompat.getColor(mContext, android.R.color.darker_gray));
        }
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(200);
        view.startAnimation(anim);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class BoardPositionHolder extends RecyclerView.ViewHolder {

        private TextView mCharacterTextView;

        BoardPositionHolder(View view) {
            super(view);

            mCharacterTextView = view.findViewById(R.id.character_text_view);
        }
    }
}
