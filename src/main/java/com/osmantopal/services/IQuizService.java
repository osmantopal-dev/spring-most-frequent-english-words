package com.osmantopal.services;

import java.util.List;


import com.osmantopal.entities.EnglishWord;
import com.osmantopal.model.QuizSession;


public interface IQuizService {

    public void startQuiz(Long chatId, List<EnglishWord> words);
    public void sendNextQuestion(Long chatId, QuizSession session);
    
}