package com.osmantopal.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osmantopal.controller.IAdminController;
import com.osmantopal.services.IEnglishWordService;
import com.osmantopal.services.IWordImporterService;

@RestController
@RequestMapping("/rest/api/admin")
public class AdminControllerImpl implements IAdminController {

    @Autowired
    private IWordImporterService wordImporterService;

    @Autowired
    private IEnglishWordService dailyWordService;

    @Override
    @PostMapping("/import-words")
    public String importWordsFromFile() {
        
        wordImporterService.importWordsFromFile();

        return "Words imported successfully!";
    }

    @PostMapping("/send-daily-words")
    public ResponseEntity<String> sendDailyWords() {
        dailyWordService.sendDailyWords();
        return ResponseEntity.ok("Günlük kelimeler gönderildi");
    }
    
}