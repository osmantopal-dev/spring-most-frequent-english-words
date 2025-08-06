package com.osmantopal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.osmantopal.entities.EnglishWord;

@Repository
public interface EnglishWordRepository extends JpaRepository<EnglishWord, Integer>{
    

    @Query(value = "SELECT * FROM english_words ORDER BY RANDOM() LIMIT :count", 
          nativeQuery = true)
    List<EnglishWord> findRandomWords(@Param("count") int count);

    @Query(value = "SELECT COUNT(*) > 0 FROM english_words WHERE english_word = :word", 
          nativeQuery = true)
    boolean existsByWord(@Param("word") String word);

}