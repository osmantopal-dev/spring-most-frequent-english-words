package com.osmantopal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "english_words")
public class EnglishWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "english_word", nullable = false, unique = true)
    private String englishWord;
    
    @Column(name = "turkish_name", nullable = false)
    private String turkishMeaning;

    @Column(name = "word_level", nullable = false)
    private String wordLevel;
    
}