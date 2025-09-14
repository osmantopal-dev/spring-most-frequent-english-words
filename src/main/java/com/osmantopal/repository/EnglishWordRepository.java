package com.osmantopal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.osmantopal.entities.EnglishWord;

@Repository
public interface EnglishWordRepository extends JpaRepository<EnglishWord, Integer>{
    

    @Query(value = """
        SELECT * FROM english_words ew
        WHERE ew.id NOT IN (
            SELECT lw.word_id FROM learned_words lw WHERE lw.subscriber_id = :subscriber_id
        )
        ORDER BY RANDOM()
        LIMIT :limit
        """, nativeQuery = true)
    List<EnglishWord> findRandomWordsNotLearnedByUser(@Param("subscriber_id") Long subscriber_id, @Param("limit") int limit);

    @Query(value = "SELECT COUNT(*) > 0 FROM english_words WHERE english_word = :word", 
          nativeQuery = true)
    boolean existsByWord(@Param("word") String word);

      @Query(value = "SELECT * FROM english_words WHERE id != :id ORDER BY RANDOM() LIMIT :count", 
              nativeQuery = true)
    List<EnglishWord> findRandomWordsExcluding(Integer id, @Param("count") int i);

}