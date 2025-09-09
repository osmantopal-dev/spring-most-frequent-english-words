package com.osmantopal.services;

import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import com.osmantopal.entities.EnglishWord;
import com.osmantopal.model.QuizSession;


public interface IQuizService {

    public void startQuiz(Long chatId, List<EnglishWord> words);
    public void sendNextQuestion(Long chatId, QuizSession session);
    public void checkAnswer(Long chatId, Integer wordId, String userAnswer);
    public void finishQuiz(Long chatId, QuizSession session);
    public InlineKeyboardMarkup createQuizKeyboard(Integer wordId, List<String> options);
    public List<String> generateOptions(EnglishWord correctWord);
    
}