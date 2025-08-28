package com.osmantopal.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscriber {

    @Id
    @Column(nullable = false)
    private Long chatId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "subscribe-date", nullable = false)
    private LocalDateTime subscribeDate;

    @ManyToMany
    @JoinTable(
        name = "learned_words",
        joinColumns = @JoinColumn(name = "subscriber_id"),
        inverseJoinColumns = @JoinColumn(name = "word_id")
    )
    private Set<EnglishWord> learnedWords = new HashSet<>();

}