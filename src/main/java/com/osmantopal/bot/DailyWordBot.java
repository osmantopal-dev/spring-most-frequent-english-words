package com.osmantopal.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.osmantopal.entities.EnglishWord;
import com.osmantopal.entities.Subscriber;
import com.osmantopal.repository.SubscriberRepository;
import com.osmantopal.services.IQuizService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DailyWordBot extends TelegramLongPollingBot {

    private final SubscriberRepository subscriberRepository;
    private IQuizService quizService;
    private final Map<Long, List<EnglishWord>> userWords = new HashMap<>();
    private final Map<Long, Integer> userWordIndex = new HashMap<>();

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    public DailyWordBot(
            @Value("${telegram.bot.token}") String token,
            SubscriberRepository subscriberRepository) {
        super(token);
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
    }

    private void handleMessage(Update update) {
        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        User telegramUser = update.getMessage().getFrom();

        if (text.equals("/start")) {
            if (!subscriberRepository.existsById(chatId)) {
                Subscriber subscriber = new Subscriber();
                subscriber.setChatId(chatId);
                subscriber.setUsername(telegramUser.getUserName());
                subscriber.setSubscribeDate(LocalDateTime.now());
                subscriberRepository.save(subscriber);
                sendMessage(chatId, "Abonelik başarılı! Her gün 09:00'da kelimeler gelecek.");
            } else {
                sendMessage(chatId, "Zaten abonesiniz! Her gün 09:00'da kelimeler alacaksınız.");
            }
        }
    }

    private void handleCallbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        if ("next_word".equals(callbackData)) {
            sendNextWord(chatId);
        }
        else if ("start_quiz".equals(callbackData)) {
            quizService.startQuiz(chatId, userWords.get(chatId));
        }
    }

    public void sendWords(Long chatId, List<EnglishWord> words) {
        userWords.put(chatId, words);
        userWordIndex.put(chatId, 0);
        sendNextWord(chatId);
    }

    private void sendNextWord(Long chatId) {
        List<EnglishWord> words = userWords.get(chatId);
        Integer index = userWordIndex.get(chatId);

        if (index < words.size()) {
            EnglishWord word = words.get(index);
            String messageText = formatWordMessage(word);
            InlineKeyboardMarkup keyboard = createNextButton();
            sendMessageWithKeyboard(chatId, messageText, keyboard);
            userWordIndex.put(chatId, index + 1);
        } else {
            sendQuizStartMessage(chatId);
            userWords.remove(chatId);
            userWordIndex.remove(chatId);
        }
    }

    private void sendQuizStartMessage(Long chatId) {
        String messageText = "Quiz'e hoş geldiniz! Başlamak için aşağıdaki butona tıklayın.";
        InlineKeyboardMarkup keyboard = createStartQuizButton();
        sendMessageWithKeyboard(chatId, messageText, keyboard);
    }

    private String formatWordMessage(EnglishWord word) {
        return "🔹 " + word.getEnglishWord() + 
               "\n📌 Anlamı: " + word.getTurkishMeaning() + 
               "\n📊 Seviye: " + word.getWordLevel();
    }

    private InlineKeyboardMarkup createNextButton() {
        InlineKeyboardButton nextButton = new InlineKeyboardButton();
        nextButton.setText("Sonraki Kelime →");
        nextButton.setCallbackData("next_word");

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(nextButton);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        return markup;
    }

    private InlineKeyboardMarkup createStartQuizButton() {
        InlineKeyboardButton startQuizButton = new InlineKeyboardButton();
        startQuizButton.setText("Start Quiz");
        startQuizButton.setCallbackData("start_quiz");

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(startQuizButton);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        return markup;
    }


    private void sendMessageWithKeyboard(Long chatId, String text, InlineKeyboardMarkup keyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        message.setReplyMarkup(keyboard);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}