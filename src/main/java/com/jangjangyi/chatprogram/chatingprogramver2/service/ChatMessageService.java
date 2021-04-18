package com.jangjangyi.chatprogram.chatingprogramver2.service;

import com.jangjangyi.chatprogram.chatingprogramver2.dto.ChatMessageForm;
import com.jangjangyi.chatprogram.chatingprogramver2.model.chat.ChatMessage;
import com.jangjangyi.chatprogram.chatingprogramver2.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final UsersService usersService;
    private final ChatRoomService chatRoomService;
    private final NoticeService noticeService;
    @Transactional
    public void save(ChatMessageForm message) {
        ChatMessage chatMessage = new ChatMessage(message.getMessage(),LocalDateTime.now(),chatRoomService.findById(message.getChatRoomId()).get()
                ,usersService.findUserByEmailMethod(message.getSender()));
        chatMessageRepository.save(chatMessage);
//        noticeService.addMessageNotice(chatMessage.getChatRoom(),chatMessage.getWriter(), message.getReceiver(),chatMessage.getTime());
    }
}
