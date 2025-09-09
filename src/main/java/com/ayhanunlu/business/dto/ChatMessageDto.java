package com.ayhanunlu.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class ChatMessageDto {

    private Long id;
    private String sender;
    private String message;
    private LocalDateTime timestamp;

    ChatMessageDto(Long id,String sender, String message, LocalDateTime timestamp) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.timestamp=timestamp;
    }
}
