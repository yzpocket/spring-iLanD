package com.yzpocket.iland.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoticeViewController {
    // 공지 화면
    @GetMapping("/announcement")
    public String noticeView(){
        return "/contents/announcement";
    }
}
