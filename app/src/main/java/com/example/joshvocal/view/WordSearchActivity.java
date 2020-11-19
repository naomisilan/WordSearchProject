package com.example.joshvocal.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.joshvocal.R;
import com.example.joshvocal.controller.WordListAdapter;
import com.example.joshvocal.controller.WordSearchAdapter;
import com.example.joshvocal.model.BoardPosition;
import com.example.joshvocal.model.Word;
import com.example.joshvocal.model.WordSearch;
import com.example.joshvocal.util.OnItemTouchMultiDragListener;

import java.util.ArrayList;
import java.util.Arrays;

public class WordSearchActivity extends AppCompatActivity {

    private RecyclerView mWordSearchRecyclerView;
    private RecyclerView mWordListRecyclerView;

    private WordSearchAdapter mWordSearchAdapter;
    private WordListAdapter mWordListAdapter;

    private WordSearch mWordSearch;
    private String mCurrentWord = "";
    private TextView mCurrentWordTextView;
    private TextView mTextField;

    private static final int NUM_ROWS = 10;
    private static final int NUM_COLS = 10;
    private static final int WORD_LIST_NUM_COLS = 3;

    private ArrayList<Word> mWords = new ArrayList<>(Arrays.asList(
            new Word("phone"),
            new Word("android"),
            new Word("food"),
            new Word("storm"),
            new Word("typhoon"),
            new Word("teacher"),
            new Word("machine"),
            new Word("learning")
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_search);

        getSupportActionBar().hide();

        try {
            mWordSearch = new WordSearch(mWords, NUM_ROWS, NUM_COLS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mCurrentWordTextView = findViewById(R.id.currentWord);

        mWordListRecyclerView = findViewById(R.id.word_list);
        mWordListRecyclerView.setLayoutManager(new GridLayoutManager(this, WORD_LIST_NUM_COLS));
        mWordListAdapter = new WordListAdapter(this, mWordSearch.getWords());
        mWordListRecyclerView.setAdapter(mWordListAdapter);

        mWordSearchRecyclerView = findViewById(R.id.recyclerView);
        mWordSearchRecyclerView.setLayoutManager(new GridLayoutManager(this, NUM_COLS));
        mWordSearchAdapter = new WordSearchAdapter(this, mWordSearch.getBoardPositionsInOneDimension());
        mWordSearchRecyclerView.setAdapter(mWordSearchAdapter);
        mTextField = findViewById(R.id.mTextField);
        new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                gameOver();
            }
        }.start();
        mWordSearchRecyclerView.addOnItemTouchListener(new OnItemTouchMultiDragListener("touchable", this, mWordSearch) {

            @Override
            public void onDragSelectionUp(BoardPosition startPos, BoardPosition endPos, BoardPosition lastPos, RecyclerView.ViewHolder holder) {
                super.onDragSelectionUp(startPos, endPos, lastPos, holder);

                mCurrentWordTextView.setText("...");

                onAfterDrag();
            }

            @Override
            public void onTouchFirstSelection(BoardPosition boardPos, int pos) {
                onSelectPositionFromBoard(boardPos, pos);
            }

            @Override
            public void onMoveSelection(BoardPosition boardPos, int pos) {
                if (!mWordSearchAdapter.isAlreadySelected(boardPos)) {
                    onSelectPositionFromBoard(boardPos, pos);
                }
            }
        });
    }

    private void onSelectPositionFromBoard(BoardPosition boardPosition, int pos) {
        System.out.println(boardPosition.getCharacter());
        mCurrentWord += Character.toString(boardPosition.getCharacter());

        mWordSearchAdapter.getSelectedPositions().addLast(boardPosition);
        boardPosition.setIsSelected(true);

        mCurrentWordTextView.setText(mCurrentWord);

        mWordSearchAdapter.notifyItemChanged(pos);
//        mWordSearchAdapter.notifyDataSetChanged();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_finish_game);
        dialog.setCanceledOnTouchOutside(false);

        Button playAgain = dialog.findViewById(R.id.playAgainButton);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        dialog.show();
    }
    private void gameOver() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fail);
        dialog.setCanceledOnTouchOutside(false);

        Button playAgain = dialog.findViewById(R.id.playAgainButton);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        dialog.show();
    }


    private void onAfterDrag() {
        boolean isWordFound = false;

        for (Word w : mWordSearch.getWords()) {
            if (w.getValue().equals(mCurrentWord.toLowerCase())) {
                isWordFound = true;
            }
        }

        if (isWordFound) {
            Word foundWord = null;
            for (Word w : mWordSearch.getWords()) {
                if (w.getValue().equals(mCurrentWord.toLowerCase())) {
                    foundWord = w;
                }
            }

            for (BoardPosition boardPosition : mWordSearchAdapter.getSelectedPositions()) {
                boardPosition.setIsWordFound(true);
            }

            foundWord.setIsFound(true);

            mWordListAdapter.notifyDataSetChanged();
        } else {
            for (BoardPosition boardPosition : mWordSearchAdapter.getSelectedPositions()) {
                boardPosition.setIsSelected(false);
                mWordSearchAdapter.notifyItemChanged(boardPosition.getRow() * NUM_ROWS + boardPosition.getCol());
            }

        }

        mCurrentWord = "";

        mWordSearchAdapter.removeSelectedPositions();
//        mWordSearchAdapter.notifyDataSetChanged();

        if (mWordSearchAdapter.isGameFinished(mWordSearch)) {
            showDialog();
//            startActivity(new Intent(this, WordSearchActivity.class));
        }
    }
}
