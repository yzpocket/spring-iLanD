package com.yzpocket.iland.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatViewController {
    // 채팅 화면
    @GetMapping("/chatting")
    public String chatView(){
        return "/contents/chatting";
    }
}
