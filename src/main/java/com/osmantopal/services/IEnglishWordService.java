package com.osmantopal.services;

import com.osmantopal.entities.EnglishWord;

public interface IEnglishWordService {
    
    public void addWord(EnglishWord word);
    public void sendDailyWords();
}