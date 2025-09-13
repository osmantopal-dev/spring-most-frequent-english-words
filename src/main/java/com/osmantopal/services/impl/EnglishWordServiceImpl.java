package com.osmantopal.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.osmantopal.bot.DailyWordBot;
import com.osmantopal.entities.EnglishWord;
import com.osmantopal.entities.Subscriber;
import com.osmantopal.repository.EnglishWordRepository;
import com.osmantopal.repository.SubscriberRepository;
import com.osmantopal.services.IEnglishWordService;

@Service
public class EnglishWordServiceImpl implements IEnglishWordService{

    @Autowired
    private EnglishWordRepository wordRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private DailyWordBot telegramBot;

    @Override
    public void addWord(EnglishWord word) {
    }

    @Override
    @Scheduled(cron = "0 0 9 * * ?", zone = "Europe/Istanbul")
    public void sendDailyWords() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        List<EnglishWord> words = wordRepository.findRandomWords(10);

        subscribers.forEach(subscriber -> {
            telegramBot.sendWords(subscriber.getChatId(), words);
        });
    }


}