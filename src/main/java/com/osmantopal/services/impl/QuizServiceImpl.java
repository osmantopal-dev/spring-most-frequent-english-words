package com.osmantopal.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.osmantopal.entities.EnglishWord;
import com.osmantopal.entities.LearnedWord;
import com.osmantopal.model.QuizSession;
import com.osmantopal.repository.EnglishWordRepository;
import com.osmantopal.repository.LearnedWordRepository;
import com.osmantopal.repository.SubscriberRepository;
import com.osmantopal.services.IBotService;
import com.osmantopal.services.IQuizService;

@Service
public class QuizServiceImpl implements IQuizService{

    @Autowired
    private EnglishWordRepository englishWordRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired 
    private LearnedWordRepository learnedWordRepository;

    @Autowired
    @Lazy
    private IBotService botService;
    


    private final Map<Long, QuizSession> activeQuizzes = new HashMap<>();

    @Override
    public void startQuiz(Long chatId, List<EnglishWord> words) {

        QuizSession session = new QuizSession(words, 0);
        activeQuizzes.put(chatId, session);
        
        sendNextQuestion(chatId, session);
    }

    @Override
    public void sendNextQuestion(Long chatId, QuizSession session) {
        if (session.hasNext()) {
            EnglishWord currentWord = session.getNextWord();
            List<String> options = generateOptions(currentWord);
            
            String question = "❓ '" + currentWord.getEnglishWord() + "' kelimesinin Türkçe karşılığı nedir?";
            InlineKeyboardMarkup keyboard = createQuizKeyboard(currentWord.getId(), options);
            
            botService.sendMessageWithKeyboard(chatId, question, keyboard);

        } else {
            finishQuiz(chatId, session);
        }
    }

    @Override
    public List<String> generateOptions(EnglishWord correctWord) {
        List<String> options = new ArrayList<>();
        options.add(correctWord.getTurkishMeaning()); // Doğru cevap
        
        // Rastgele 3 yanlış cevap
        List<EnglishWord> randomWords = englishWordRepository.findRandomWordsExcluding(correctWord.getId(), 3);
        
        randomWords.forEach(word -> options.add(word.getTurkishMeaning()));
        Collections.shuffle(options); // Karıştır
        
        return options;
    }

    @Override
    public InlineKeyboardMarkup createQuizKeyboard(Integer wordId, List<String> options) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        
        for (int i = 0; i < options.size(); i++) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(options.get(i));
            button.setCallbackData("quiz_" + wordId + "_" + options.get(i));
            
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }
        
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        return markup;
    }

    @Override
    public void checkAnswer(Long chatId, Integer wordId, String userAnswer) {
        QuizSession session = activeQuizzes.get(chatId);
        if (session == null) return;

        EnglishWord word = englishWordRepository.findById(wordId).orElseThrow();
        boolean isCorrect = word.getTurkishMeaning().equalsIgnoreCase(userAnswer);
        
        if (isCorrect) {
            botService.sendMessage(chatId, "✅ Doğru!");
            LearnedWord learnedWord = new LearnedWord();
            learnedWord.setWord(word);
            // learnedWord.setLearnedDate(java.time.LocalDateTime.now()); date  sonra eklenecek
            learnedWord.setSubscriber(subscriberRepository.findByChatId(chatId)); 
            learnedWordRepository.save(learnedWord); 
            // englishWordRepository.delete(word);

        } else {
            botService.sendMessage(chatId, "❌ Yanlış! Doğrusu: " + word.getTurkishMeaning());
        }
        
        sendNextQuestion(chatId, session);
    }

    @Override
    public void finishQuiz(Long chatId, QuizSession session) {
        String result = "🎉 Quiz tamamlandı!\n";
        
        botService.sendMessage(chatId, result);
        activeQuizzes.remove(chatId);
        
    }



}