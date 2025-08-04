package com.osmantopal.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.osmantopal.entities.DailyWordBot;
import com.osmantopal.entities.EnglishWord;
import com.osmantopal.entities.Subscriber;
import com.osmantopal.repository.IEnglishWordRepository;
import com.osmantopal.repository.ISubscriberRepository;
import com.osmantopal.services.IEnglishWordService;

@Service
public class EnglishWordServiceImpl implements IEnglishWordService{

    @Autowired
    private IEnglishWordRepository wordRepository;

    @Autowired
    private ISubscriberRepository subscriberRepository;

    private DailyWordBot telegramBot;

    @Override
    public void addWord(EnglishWord word) {
    }

    @Override
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDailyWords() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        List<EnglishWord> words = wordRepository.findRandomWords(5); // 5 rastgele kelime
        
        subscribers.forEach(subscriber -> {
            StringBuilder message = new StringBuilder("📅 Günün Kelimeleri:\n\n");
            
            words.forEach(word -> {
                message.append("🔹 ")
                    .append(word.getEnglishWord())
                    .append(": ")
                    .append(word.getTurkishMeaning())
                    .append("\n\n");
            });
            
            telegramBot.sendMessage(subscriber.getChatId(), message.toString());
        });
    }

    
    

}