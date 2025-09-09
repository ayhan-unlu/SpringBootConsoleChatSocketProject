package com.ayhanunlu.data.repository;

import com.ayhanunlu.data.entity.ChatMessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessageEntity,Long> {
}
