package com.example.joshvocal.util;

import com.example.joshvocal.model.Word;

import java.util.Comparator;

public class LengthFirstComparator implements Comparator<Word> {

    @Override
    public int compare(Word word1, Word word2) {
        if (word1.getValue().length() < word2.getValue().length()) {
            return 1;
        } else if (word1.getValue().length() > word2.getValue().length()) {
            return -1;
        } else {
            return 0;
        }
    }
}
