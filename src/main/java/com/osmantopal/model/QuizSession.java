package com.osmantopal.model;

import java.util.List;

import com.osmantopal.entities.EnglishWord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizSession {
    
    private List<EnglishWord> words;
    private int currentIndex;


    public boolean hasNext() {
        return currentIndex < words.size();
    }


    public EnglishWord getNextWord() {
        return words.get(currentIndex++);
    }
    
}