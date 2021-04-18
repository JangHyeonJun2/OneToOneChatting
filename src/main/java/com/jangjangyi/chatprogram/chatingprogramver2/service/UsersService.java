package com.jangjangyi.chatprogram.chatingprogramver2.service;

import com.jangjangyi.chatprogram.chatingprogramver2.dto.UserLoginDto;
import com.jangjangyi.chatprogram.chatingprogramver2.dto.UserSignupDto;
import com.jangjangyi.chatprogram.chatingprogramver2.model.Users;
import com.jangjangyi.chatprogram.chatingprogramver2.repository.UsersRepository;
import com.jangjangyi.chatprogram.chatingprogramver2.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;
    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
    public Users findUserByEmailMethod(String userEmail) {
        return usersRepository.findUsersByEmail(userEmail);
    }

    @Transactional
    public Users register(UserSignupDto userSignupDto) {
        Users newUser = new Users();
        newUser.setName(userSignupDto.getUserId());;
        newUser.setEmail(userSignupDto.getUserEmail());
        newUser.setPassword(userSignupDto.getUserPwd());
        Users saveUser = usersRepository.save(newUser);
        return saveUser;
    }

    public boolean exitingEmail(String userEamil) {
        return usersRepository.existsByEmail(userEamil);
    }

    public String createToken(UserLoginDto requestDto) {
        return jwtTokenProvider.createToken(requestDto.getUserEmail());
    }

    public Users findByName(String sender) {
        return usersRepository.findByName(sender);
    }






}
