package com.jangjangyi.chatprogram.chatingprogramver2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupDto {
    private String userId;
    private String userPwd;
    private String userEmail;
}
