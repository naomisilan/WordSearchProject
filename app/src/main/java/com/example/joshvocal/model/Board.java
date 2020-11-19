package com.example.joshvocal.model;

import java.io.Serializable;

public class Board implements Serializable {

    private int mNumRows;
    private int mNumCols;
    private BoardPosition[][] mBoardPositions;

    public Board(int rows, int cols) {
        this.mNumRows = rows;
        this.mNumCols = cols;
        this.mBoardPositions = new BoardPosition[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                mBoardPositions[row][col] = new BoardPosition(row, col);
            }
        }
    }

    public int getNumRows() {
        return mNumRows;
    }

    public int getNumCols() {
        return mNumCols;
    }

    public BoardPosition getBoardPosition(int row, int col) {
        return mBoardPositions[row][col];
    }

    public int getRowFromPosition(int position) {
        return position / mNumRows;
    }

    public int getColFromPosition(int position) {
        return position % mNumCols;
    }

    public BoardPosition getBoardPosition(int position) {
        int row = position / mNumRows;
        int col = position % mNumCols;
        return mBoardPositions[row][col];
    }
}
