package com.example.joshvocal.model;

import java.io.Serializable;

public class Word implements Serializable {

    private String mValue;
    private BoardPosition mStartPosition;
    private BoardPosition mEndPosition;
    private boolean mIsFound = false;

    public Word(String value) {
        this.mValue = value;
    }

    public String getValue() {
        return mValue;
    }

    public BoardPosition getStartPosition() {
        return mStartPosition;
    }

    public BoardPosition getEndPosition() {
        return mEndPosition;
    }

    public void setStartPosition(int row, int col) {
        this.mStartPosition = new BoardPosition(row, col);
    }

    public void setEndPosition(int row, int col) {
        this.mEndPosition = new BoardPosition(row, col);
    }

    public boolean isFound() {
        return mIsFound;
    }

    public void setIsFound(boolean isFound) {
        mIsFound = isFound;
    }
}
