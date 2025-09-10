package com.ayhanunlu.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

//Lombok Data Annotations
@Data
@NoArgsConstructor
@Builder

@Entity
@Table(name = "chat_history")
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "sender", nullable = false)
    private String sender;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public ChatMessageEntity(Long id,String sender, String message, LocalDateTime timestamp) {
        this.id=id;
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }
}
