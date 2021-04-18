package com.jangjangyi.chatprogram.chatingprogramver2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginDto {
    private String userPwd;
    private String userEmail;

}
