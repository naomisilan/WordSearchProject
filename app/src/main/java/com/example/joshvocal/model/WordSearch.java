package com.example.joshvocal.model;

import android.util.Log;

import com.example.joshvocal.util.LengthFirstComparator;
import com.example.joshvocal.util.ObjectClone;
import com.example.joshvocal.view.BoardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import static android.content.ContentValues.TAG;

public class WordSearch {

    private Board mBoard;
    private ArrayList<Word> mWords;

    public WordSearch(ArrayList<Word> words, int numRows, int numCols) throws Exception {
        this.mWords = words;
        this.mBoard = generateBoard(numRows, numCols);
    }

    public Board getBoard() {
        return mBoard;
    }

    public ArrayList<Word> getWords() {
        return mWords;
    }

    private Board generateBoard(int numRows, int numCols) throws Exception {
        Board board = new Board(numRows, numCols);

        Collections.sort(mWords, new LengthFirstComparator());

        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.EAST);
        directions.add(Direction.SOUTH);

        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < board.getNumRows() * board.getNumCols(); i++) {
            positions.add(i);
        }

        Stack<BoardState> boardStates = new Stack<>();
        ArrayList<Direction> directionsCopy = (ArrayList<Direction>) ObjectClone.deepCopy(directions);
        ArrayList<Integer> positionsCopy = (ArrayList<Integer>) ObjectClone.deepCopy(positions);
        ArrayList<Word> wordsCopy = (ArrayList<Word>) ObjectClone.deepCopy(mWords);
        Collections.shuffle(directionsCopy);
        Collections.shuffle(positionsCopy);
        boardStates.push(new BoardState(board, wordsCopy.remove(0), directionsCopy, positionsCopy));

        Board generatedBoard = null;
        ArrayList<Word> wordListBackup = new ArrayList<>();

        while (true) {

            // If our current state is empty, we've backtracked all the way to the beginning have used all
            // our possibilities
            BoardState currentBoardState = !boardStates.isEmpty() ? boardStates.peek() : null;
            if (currentBoardState == null) {
                Log.d(TAG, "generateBoard: No Possible Solutions");
                break;
            }

            Direction direction = !currentBoardState.mDirection.isEmpty() ? currentBoardState.mDirection.remove(0) : null;
            if (direction == null) {

                // Tried all possible directions in this position, remove this position from unvisited positions
                // and reset our possible directions for this new unvisited position
                if (!currentBoardState.mPositions.isEmpty()) {
                    currentBoardState.mPositions.remove(0);
                }
                currentBoardState.mDirection = (ArrayList<Direction>) ObjectClone.deepCopy(directions);
                Collections.shuffle(currentBoardState.mDirection);
                direction = currentBoardState.mDirection.remove(0);
            }

            Integer position = !currentBoardState.mPositions.isEmpty() ? currentBoardState.mPositions.get(0) : null;

            if (position == null) {
                // We've tried all possible grid positions for this grid state, add the word back and
                // try a different unvisited grid state
                wordsCopy.add(currentBoardState.mWord);
                boardStates.pop();
            } else {
                generatedBoard = tryPlaceWord(currentBoardState.mBoard, currentBoardState.mWord, position, direction);
                if (generatedBoard != null) {
                    wordListBackup.add(currentBoardState.mWord);

                    // We have a valid grid and there are still more words to place
                    if (!wordsCopy.isEmpty()) {
                        Collections.shuffle(positions);
                        Collections.shuffle(directions);
                        boardStates.push(new BoardState(generatedBoard, wordsCopy.remove(0), directionsCopy, positions));
                    } else {

                        // Used up all our words we wanted to place
                        break;
                    }
                }
            }
        }

        if (generatedBoard != null) {
            BoardView boardView = new BoardView();
            boardView.printWordSearch(generatedBoard);
            for (Word w : wordListBackup) {
                System.out.println(w.getValue());
                System.out.printf("Start: (%d, %d)\n", w.getStartPosition().getRow(), w.getStartPosition().getCol());
                System.out.printf("End: (%d, %d)\n", w.getEndPosition().getRow(), w.getEndPosition().getCol());
            }
        }

        fillRemainingBoard(generatedBoard);

        mWords = wordListBackup;

        return generatedBoard;
    }

    public static void fillRemainingBoard(Board board) {
        for (int row = 0; row < board.getNumRows(); row++) {
            for (int col = 0; col < board.getNumCols(); col++) {
                if (board.getBoardPosition(row, col).isEmpty()) {
                    char c = (char) ('A' + Math.random() * ('Z' - 'A'));
                    board.getBoardPosition(row, col).setCharacter(c);
                }
            }
        }
    }

    public static Board tryPlaceWord(Board board, Word currentWord, int currentPosition, Direction direction) throws Exception {
        Board tempBoard = (Board) ObjectClone.deepCopy(board);
        BoardView boardView = new BoardView();

        // Row and Column of the board we are trying to place our current word.
        int gridRow = tempBoard.getRowFromPosition(currentPosition);
        int gridCol = tempBoard.getColFromPosition(currentPosition);
        currentWord.setStartPosition(gridRow, gridCol);

        // X and Y coordinates of the direction we are trying
        int directionRow = direction.getX();
        int directionCol = direction.getY();

        // Turn our current word into an array of characters we can place one at a time
        ArrayList<Character> letters = new ArrayList<>();
        for (char c : currentWord.getValue().toCharArray()) {
            letters.add(c);
        }

        // Check if our current row and board positions are valid
        while ((gridRow >= 0 && gridRow < tempBoard.getNumRows()) && (gridCol >= 0 && gridCol < tempBoard.getNumCols())) {

            // We've used all of our letters
            if (letters.isEmpty()) {
                currentWord.setEndPosition(gridRow, gridCol);
                break;
            }

            // Pop our current letter from our list of available letters to place
            char currentLetter = letters.remove(0);

            // Place our current letter into an empty board position or one that has the same letter
            // Try another position and direction if the board position is invalid
            if (tempBoard.getBoardPosition(gridRow, gridCol).isEmpty()) {
                tempBoard.getBoardPosition(gridRow, gridCol).setCharacter(Character.toUpperCase(currentLetter));
                gridRow += directionRow;
                gridCol += directionCol;
            } else {
                return null;
            }

            boardView.printWordSearch(tempBoard);
        }

        // We have place a letter and stepped out of bounds, subtract the previous direction we went
        // to get row and col
        if (letters.isEmpty()) {
            gridCol -= directionCol;
            gridRow -= directionRow;
            currentWord.setEndPosition(gridRow, gridCol);
        }

        // All letters used means that we have successfully place our word
        return letters.isEmpty() ? tempBoard : null;
    }

    public ArrayList<BoardPosition> getBoardPositionsInOneDimension() {
        ArrayList<BoardPosition> boardPositions = new ArrayList<>();

        for (int i = 0; i < getBoard().getNumRows(); i++) {
            for (int j = 0; j < getBoard().getNumCols(); j++) {
                boardPositions.add(getBoard().getBoardPosition(i, j));
            }
        }

        return boardPositions;
    }
}
