package com.osmantopal.controller.impl;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osmantopal.controller.IHomeController;

@RestController
public class HomeControllerImpl implements IHomeController {
    

    @Override
    @GetMapping
    public String getHome() {
        
        return "Welcome to the Most Frequent English Words API!";
    }
}