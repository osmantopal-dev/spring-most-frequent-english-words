package com.osmantopal.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osmantopal.controller.ISubscriberController;
import com.osmantopal.entities.Subscriber;
import com.osmantopal.services.ISubscriberService;

@RestController
@RequestMapping("/rest/api/subscribers")
public class SubscriberControllerImpl implements ISubscriberController{

    @Autowired
    private ISubscriberService subscriberService;

    @Override
    @GetMapping(path = "/all")
    public List<Subscriber> getAllSubscribers() {
        
        return subscriberService.getAllSubscribers();
    }
    
    

}