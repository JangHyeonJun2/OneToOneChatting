package com.jangjangyi.chatprogram.chatingprogramver2.repository;

import com.jangjangyi.chatprogram.chatingprogramver2.model.Users;
import com.jangjangyi.chatprogram.chatingprogramver2.model.chat.ChatRoom;
import com.jangjangyi.chatprogram.chatingprogramver2.model.chat.ChatRoomJoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomJoinRepository extends JpaRepository<ChatRoomJoin,Long> {
    public List<ChatRoomJoin> findByUser(Users user);
    public List<ChatRoomJoin> findByChatRoom(ChatRoom chatRoom);
}
