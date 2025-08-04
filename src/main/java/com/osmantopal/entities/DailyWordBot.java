package com.osmantopal.entities;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.osmantopal.repository.ISubscriberRepository;


@Component
public class DailyWordBot extends TelegramLongPollingBot {

    @Autowired
    private ISubscriberRepository subscriberRepository;

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public DailyWordBot(
        @Value("${telegram.bot.username}") String username,
        @Value("${telegram.bot.token}") String token
    ) {
        super(token);
        this.username = username;
        this.token = token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            
            User telegramUser = update.getMessage().getFrom(); 
            String username = telegramUser.getUserName(); 
            
            if (text.equals("/start")) {
                Subscriber subscriber = new Subscriber();
                subscriber.setChatId(chatId);
                subscriber.setUsername(username);
                subscriber.setSubscribeDate(LocalDateTime.now());
                
                subscriberRepository.save(subscriber);
                
                sendMessage(chatId, "Abonelik başarılı! Her gün 09:00'da kelimeler gelecek.");
            }
        }
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId.toString(), text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
