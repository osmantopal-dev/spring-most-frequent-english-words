package com.osmantopal.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osmantopal.controller.IEnglishWordController;
import com.osmantopal.services.IEnglishWordService;

@RestController
@RequestMapping("/rest/api/words")
public class EnglishWordControllerImpl implements IEnglishWordController{

    @Autowired
    private IEnglishWordService dailyWordService;

    @PostMapping("/send-daily-words")
    public ResponseEntity<String> sendDailyWords() {
        dailyWordService.sendDailyWords();
        return ResponseEntity.ok("Günlük kelimeler gönderildi");
    }


}