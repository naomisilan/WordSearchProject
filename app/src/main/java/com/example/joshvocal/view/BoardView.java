package com.example.joshvocal.view;

import com.example.joshvocal.model.Board;

public class BoardView {

    public void printWordSearch(Board board) {
        System.out.println("Generated Board State:");

        for (int i = 0; i < board.getNumRows(); i++) {
            for (int j = 0; j < board.getNumCols(); j++) {
                System.out.print(board.getBoardPosition(i, j).getCharacter() + "  ");
            }
            System.out.println();
        }

        System.out.println();
    }
}
