package com.ayhanunlu.business.service.impl;

import com.ayhanunlu.business.dto.ChatMessageDto;
import com.ayhanunlu.business.service.IChatMessageService;
import com.ayhanunlu.data.entity.ChatMessageEntity;
import com.ayhanunlu.data.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageServiceImpl implements IChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public void saveMessage (ChatMessageDto chatMessageDto){
        ChatMessageEntity chatMessageEntity = convertDtoToEntity(chatMessageDto);
        chatMessageRepository.save(chatMessageEntity);;
    }

    public ChatMessageEntity convertDtoToEntity(ChatMessageDto chatMessageDto){
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setId(chatMessageDto.getId());
        chatMessageEntity.setTimestamp(chatMessageDto.getTimestamp());
        chatMessageEntity.setSender(chatMessageDto.getSender());
        chatMessageEntity.setMessage(chatMessageDto.getMessage());
        return chatMessageEntity;
    }

    public ChatMessageDto convertEntityToDto(ChatMessageEntity chatMessageEntity){
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setId(chatMessageEntity.getId());
        chatMessageDto.setTimestamp(chatMessageEntity.getTimestamp());
        chatMessageDto.setSender(chatMessageEntity.getSender());
        chatMessageDto.setMessage(chatMessageEntity.getMessage());
        return chatMessageDto;
    }
}
