package com.jangjangyi.chatprogram.chatingprogramver2.controller;

import com.jangjangyi.chatprogram.chatingprogramver2.dto.ChatRoomForm;
import com.jangjangyi.chatprogram.chatingprogramver2.model.Users;
import com.jangjangyi.chatprogram.chatingprogramver2.model.chat.ChatMessage;
import com.jangjangyi.chatprogram.chatingprogramver2.model.chat.ChatRoom;
import com.jangjangyi.chatprogram.chatingprogramver2.model.chat.ChatRoomJoin;
import com.jangjangyi.chatprogram.chatingprogramver2.service.ChatRoomJoinService;
import com.jangjangyi.chatprogram.chatingprogramver2.service.ChatRoomService;
import com.jangjangyi.chatprogram.chatingprogramver2.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {
    private final UsersService usersService;
    private final ChatRoomJoinService chatRoomJoinService;
    private final ChatRoomService chatRoomService;


    @GetMapping("/chat/findEmail")
    @ResponseBody
    public String chatFindEmail(HttpServletRequest request) {
        String email = (String) request.getAttribute("userEmail");
        System.out.println(email);
        return email;
    }
    @GetMapping("/chat")
    public String chatHome(@RequestParam("email") String email, Model model) {
        model.addAttribute("userEmail",email);
        Users user = usersService.findUserByEmailMethod(email);

        List<ChatRoomJoin> chatRoomJoins = chatRoomJoinService.findByUser(user);
        List<ChatRoomForm> chatRooms = chatRoomService.setting(chatRoomJoins, user);
        model.addAttribute("chatRooms",chatRooms);

        if(user == null){
            model.addAttribute("userName","");
            model.addAttribute("userId",0);
        }else{
            model.addAttribute("userName",user.getName());
            model.addAttribute("userId",user.getId());
        }
        return "room";
    }

    @PostMapping("/chat/newChat")
    public String newChat(@RequestParam("receiver") String user1, @RequestParam("sender") String user2){//, RedirectAttributes redirectAttributes
        System.out.println(user1+ " " + user2);
        Long chatRoomId = chatRoomJoinService.newRoom(user1,user2);
//        redirectAttributes.addAttribute("email",user2);
        return "redirect:/personalChat/?chatRoomId=" + chatRoomId + "&email=" + user2;
//        return "redirect:/personalChat/" + chatRoomId;
    }

    @RequestMapping(value = {"/personalChat","/personalChat"})
    public String goChat(@RequestParam("chatRoomId") Long chatRoomId,  @RequestParam("email") String email, Model model ){
//        String email = (String) request.getAttribute("userEmail");
        Users userByEmailMethod = usersService.findUserByEmailMethod(email);
        Optional<ChatRoom> opt = chatRoomService.findById(chatRoomId);
        ChatRoom chatRoom = opt.get();
        List<ChatMessage> messages = chatRoom.getMessages();
        Collections.sort(messages, (t1, t2) -> {
            if(t1.getId() > t2.getId()) return -1;
            else return 1;
        });
        if(userByEmailMethod == null){
            model.addAttribute("userName","");
            model.addAttribute("userId",0);
        }
        else{
            model.addAttribute("userName",userByEmailMethod.getName());
            model.addAttribute("userId",userByEmailMethod.getId());
        }
        List<ChatRoomJoin> list = chatRoomJoinService.findByChatRoom(chatRoom);
        model.addAttribute( "messages",messages);
        model.addAttribute("userEmail",userByEmailMethod.getEmail());

        model.addAttribute("chatRoomId",chatRoomId);
        int cnt = 0;
        for(ChatRoomJoin join : list){
            if(!join.getUser().getName().equals(userByEmailMethod.getName())){
                model.addAttribute("receiver",join.getUser().getName());
                ++cnt;
            }
        }
        if(cnt >= 2){
            return "redirect:/chat";
        }
        if(cnt == 0){
            model.addAttribute("receiver","");
        }
        return "roomdetail";
    }


}
