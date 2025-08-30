package com.osmantopal.services;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;


public interface IBotService {
    void sendMessage(Long chatId, String text);
    void sendMessageWithKeyboard(Long chatId, String text, InlineKeyboardMarkup keyboard);
}