package com.example.joshvocal.model;

import java.util.ArrayList;

public class BoardState {

    public Board mBoard;
    public Word mWord;
    public ArrayList<Direction> mDirection;
    public ArrayList<Integer> mPositions;

    public BoardState(Board board, Word word, ArrayList<Direction> direction, ArrayList<Integer> positions) {
        this.mBoard = board;
        this.mWord = word;
        this.mDirection = direction;
        this.mPositions = positions;
    }
}
