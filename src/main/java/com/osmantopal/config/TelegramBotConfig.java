package com.osmantopal.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.osmantopal.bot.DailyWordBot;

@Configuration
public class TelegramBotConfig {

    private final DailyWordBot dailyWordBot;

    public TelegramBotConfig(DailyWordBot dailyWordBot) {
        this.dailyWordBot = dailyWordBot;
    }

    @PostConstruct
    public void start() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(dailyWordBot);
            System.out.println("Bot başarıyla başlatıldı!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
