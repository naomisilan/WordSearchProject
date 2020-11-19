package com.example.joshvocal.model;

import java.io.Serializable;

public class BoardPosition extends Position implements Serializable {

    private char mCharacter = '.';
    private boolean mIsSelected = false;
    private boolean mIsWordFound = false;

    public BoardPosition(int row, int col) {
        super(row, col);
    }

    public void setCharacter(char character) {
        mCharacter = character;
    }

    public char getCharacter() {
        return mCharacter;
    }

    public boolean isEmpty() {
        return mCharacter == '.';
    }

    public boolean getIsSelected() {
        return mIsSelected;
    }

    public void setIsSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }

    public boolean isWordFound() {
        return mIsWordFound;
    }

    public void setIsWordFound(boolean isWordFound) {
        mIsWordFound = isWordFound;
    }

    @Override
    public boolean equals(Object obj) {
        BoardPosition bp = (BoardPosition) obj;
        return getRow() == bp.getRow() &&
                getCol() == bp.getCol() &&
                getCharacter() == bp.getCharacter();
    }
}
