package com.osmantopal.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "learned_words")
public class LearnedWord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id", nullable = false)
    private Subscriber subscriber;

    @ManyToOne
    @JoinColumn(name = "word_id", nullable = false)
    private EnglishWord word;

    private LocalDateTime learnedDate;
    
    @PrePersist
    protected void onCreate() {
        learnedDate = LocalDateTime.now();
    }
}