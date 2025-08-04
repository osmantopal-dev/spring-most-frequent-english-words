package com.osmantopal.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    @Column(name = "subscribe-date", nullable = false, unique = true)
    private LocalDateTime subscribeDate;
}