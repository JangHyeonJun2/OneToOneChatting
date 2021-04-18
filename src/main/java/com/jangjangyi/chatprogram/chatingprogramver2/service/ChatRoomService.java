package com.jangjangyi.chatprogram.chatingprogramver2.service;

import com.jangjangyi.chatprogram.chatingprogramver2.dto.ChatRoomForm;
import com.jangjangyi.chatprogram.chatingprogramver2.model.Users;
import com.jangjangyi.chatprogram.chatingprogramver2.model.chat.ChatMessage;
import com.jangjangyi.chatprogram.chatingprogramver2.model.chat.ChatRoom;
import com.jangjangyi.chatprogram.chatingprogramver2.model.chat.ChatRoomJoin;
import com.jangjangyi.chatprogram.chatingprogramver2.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomJoinService chatRoomJoinService;

    public Optional<ChatRoom> findById(Long id) {
        return chatRoomRepository.findById(id);
    }

    public List<ChatRoomForm> setting(List<ChatRoomJoin> chatRoomJoins, Users user) {
        List<ChatRoomForm> chatRooms = new ArrayList<>();
        for(ChatRoomJoin tmp : chatRoomJoins){
            ChatRoomForm chatRoomForm = new ChatRoomForm();
            ChatRoom chatRoom = tmp.getChatRoom();
            chatRoomForm.setId(chatRoom.getId());
            if(chatRoom.getMessages().size() != 0) {
                Collections.sort(chatRoom.getMessages(), new Comparator<ChatMessage>() {
                    @Override
                    public int compare(ChatMessage c1, ChatMessage c2) {
                        if(c1.getTime().isAfter(c2.getTime())){
                            return -1;
                        }
                        else{
                            return 1;
                        }
                    }
                });
                ChatMessage lastMessage = chatRoom.getMessages().get(0);
                chatRoomForm.makeChatRoomForm(lastMessage.getMessage(),chatRoomJoinService.findAnotherUser(chatRoom, user.getName()),lastMessage.getTime());
                chatRooms.add(chatRoomForm);
            }
            else{
                chatRoomJoinService.delete(tmp);
            }
        }
        Collections.sort(chatRooms, new Comparator<ChatRoomForm>() {
            @Override
            public int compare(ChatRoomForm c1, ChatRoomForm c2) {
                if(c1.getTime().isAfter(c2.getTime())){
                    return -1;
                }
                else{
                    return 1;
                }
            }
        });
        return chatRooms;
    }
}
