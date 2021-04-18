package com.jangjangyi.chatprogram.chatingprogramver2.controller;

import com.jangjangyi.chatprogram.chatingprogramver2.dto.UserLoginDto;
import com.jangjangyi.chatprogram.chatingprogramver2.dto.UserSignupDto;
import com.jangjangyi.chatprogram.chatingprogramver2.model.Notice;
import com.jangjangyi.chatprogram.chatingprogramver2.model.Users;
import com.jangjangyi.chatprogram.chatingprogramver2.response.TokenResponse;
import com.jangjangyi.chatprogram.chatingprogramver2.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UsersService userService;

    @PostMapping("/api/login")
    public Object login(@RequestBody UserLoginDto userLoginDto) {
//        Users users = userService.findUserByEmailMethod(userLoginDto.getUserEmail());
        String token = userService.createToken(userLoginDto);
        if (userService.exitingEmail(userLoginDto.getUserEmail()))
            return ResponseEntity.ok().body(new TokenResponse(token,"bearer"));
        else
            return ResponseEntity.badRequest();
    }

    @PostMapping("/api/signup")
    public Users signupMethod(@RequestBody UserSignupDto userSignupDto) {
        return userService.register(userSignupDto);
    }

    @GetMapping("/users/emailChk/{email}")
    public String nameChk(@PathVariable("email") String email){
        if(userService.findUserByEmailMethod(email)!= null){
            return "success";
        }
        return "false";
    }


    @GetMapping("/users/notice")
    public Map<String,Object> getNotice(String name){
        Map<String,Object> map = new HashMap<>();
        Users user = userService.findByName(name);
        List<String> contents = new ArrayList<>();
        List<String> links = new ArrayList<>();
        List<LocalDateTime> times = new ArrayList<>();
        List<Notice> notices = user.getNotices();
        for(Notice notice : notices){
            contents.add(notice.getContent());
            links.add(notice.getLink());
            times.add(notice.getTime());
        }
        map.put("content",contents);
        map.put("link",links);
        map.put("time",times);
        return map;
    }

}
