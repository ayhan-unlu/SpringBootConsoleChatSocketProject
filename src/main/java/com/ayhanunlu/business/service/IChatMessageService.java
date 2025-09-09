package com.ayhanunlu.business.service;

import com.ayhanunlu.business.dto.ChatMessageDto;

public interface IChatMessageService {
    void saveMessage(ChatMessageDto chatMessageDto);
}
