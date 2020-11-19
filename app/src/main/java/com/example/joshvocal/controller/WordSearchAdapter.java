package com.example.joshvocal.controller;

import android.content.Context;

import com.example.joshvocal.model.BoardPosition;
import com.example.joshvocal.model.Word;
import com.example.joshvocal.model.WordSearch;

import java.util.LinkedList;
import java.util.List;

public class WordSearchAdapter extends BoardPositionAdapter {

    private LinkedList<BoardPosition> mSelectedPositions;

    public WordSearchAdapter(Context context, List<BoardPosition> list) {
        super(context, list);

        mSelectedPositions = new LinkedList<>();
    }

    public LinkedList<BoardPosition> getSelectedPositions() {
        return mSelectedPositions;
    }

    public boolean isAlreadySelected(BoardPosition boardPosition) {
        return mSelectedPositions.contains(boardPosition);
    }

    public void removeSelectedPositions() {
        mSelectedPositions = new LinkedList<>();
    }

    public boolean isGameFinished(WordSearch wordSearch) {
        for (Word word : wordSearch.getWords()) {
            if (!word.isFound()) {
                System.out.println("Not all words have been found");
                return false;
            }
        }

        System.out.println("All words have been found");
        return true;
    }
}
