package com.jangjangyi.chatprogram.chatingprogramver2.repository;

import com.jangjangyi.chatprogram.chatingprogramver2.model.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
