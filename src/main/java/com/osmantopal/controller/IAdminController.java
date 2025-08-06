package com.osmantopal.controller;

import org.springframework.http.ResponseEntity;

public interface IAdminController {
    public String importWordsFromFile();
    public ResponseEntity<String> sendDailyWords();
}