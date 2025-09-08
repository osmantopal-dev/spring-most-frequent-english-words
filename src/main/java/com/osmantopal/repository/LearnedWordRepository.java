package com.osmantopal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osmantopal.entities.LearnedWord;

@Repository
public interface LearnedWordRepository extends JpaRepository<LearnedWord, Long> {

    
    

}