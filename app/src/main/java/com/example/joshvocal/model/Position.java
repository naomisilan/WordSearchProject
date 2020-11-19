package com.example.joshvocal.model;

import java.io.Serializable;

public class Position implements Serializable {

    private int mRow;
    private int mCol;

    public Position(int row, int col) {
        this.mRow = row;
        this.mCol = col;
    }

    public int getRow() {
        return mRow;
    }

    public int getCol() {
        return mCol;
    }
}
