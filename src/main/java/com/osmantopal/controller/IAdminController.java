package com.osmantopal.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public interface IAdminController {
    public String importWordsFromFile();
}