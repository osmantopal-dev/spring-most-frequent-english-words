package com.osmantopal.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmantopal.entities.Subscriber;
import com.osmantopal.repository.SubscriberRepository;
import com.osmantopal.services.ISubscriberService;

@Service
public class SubscriberServiceImpl implements ISubscriberService{

    @Autowired
    private SubscriberRepository subscriberRepository;
    
    @Override
    public List<Subscriber> getAllSubscribers() {

        return subscriberRepository.findAll();
    }
    
}