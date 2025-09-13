package com.osmantopal.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osmantopal.controller.IAdminController;
import com.osmantopal.services.IWordImporterService;

@RestController
@RequestMapping("/rest/api/admin")
public class AdminControllerImpl implements IAdminController {

    @Autowired
    private IWordImporterService wordImporterService;

    @Override
    @PostMapping("/import-words")
    public String importWordsFromFile() {
        
        wordImporterService.importWordsFromFile();

        return "Words imported successfully!";
    }

    
    
}