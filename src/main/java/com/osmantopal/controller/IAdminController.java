package com.osmantopal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface IAdminController {
    public String importWordsFromFile();
    public ResponseEntity<String> sendDailyWords();
}