package com.yzpocket.iland.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagementViewController {
    // 관리자 화면
    @GetMapping("/admin")
    public String adminView() {
        return "/admin/employee_main";
    }

    // 공지 관리 화면
    @GetMapping("/management_announcement")
    public String noticeView(){
        return "/admin/management_announcement";
    }

    // 컨텐츠 관리 화면
    @GetMapping("/management_contents")
    public String contentsView(){
        return "/admin/management_contents";
    }

    // 채팅 관리 화면
    @GetMapping("/management_chatting")
    public String chatView(){
        return "/admin/management_chatting";
    }
}
