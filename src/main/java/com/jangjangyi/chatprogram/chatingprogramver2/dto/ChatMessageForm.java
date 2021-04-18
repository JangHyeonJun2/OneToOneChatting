package com.jangjangyi.chatprogram.chatingprogramver2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ChatMessageForm {
    private Long ChatRoomId;
    private String receiver;
    private String sender;
    private String message;
}
