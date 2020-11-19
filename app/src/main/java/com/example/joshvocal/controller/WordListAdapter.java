package com.example.joshvocal.controller;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joshvocal.R;
import com.example.joshvocal.model.Word;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordListHolder> {

    private Context mContext;
    private List<Word> mWordList;

    public WordListAdapter(Context context, List<Word> wordList) {
        mContext = context;
        mWordList = wordList;
    }

    @NonNull
    @Override
    public WordListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_word_list, viewGroup, false);

        return new WordListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListHolder wordListHolder, int position) {
        Word currentWord = mWordList.get(position);

        wordListHolder.mWordTextView.setText(currentWord.getValue());

        if (currentWord.isFound()) {
            wordListHolder.mWordTextView.setPaintFlags(wordListHolder.mWordTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            wordListHolder.mWordTextView.setPaintFlags(wordListHolder.mWordTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    class WordListHolder extends RecyclerView.ViewHolder {

        private TextView mWordTextView;

        WordListHolder(@NonNull View itemView) {
            super(itemView);

            mWordTextView = itemView.findViewById(R.id.item_word);
        }
    }
}
