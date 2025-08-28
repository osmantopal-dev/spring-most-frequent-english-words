package com.osmantopal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.osmantopal.entities.EnglishWord;

@Service
public interface IQuizService {

    public void startQuiz(Long chatId, List<EnglishWord> words);
    
}