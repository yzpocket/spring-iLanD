package com.yzpocket.iland.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    // 동의화면
    @GetMapping("/agreement")
    public String agreementView(){
        return "member/agreement";
    }

    // 회원가입화면
    @GetMapping("/signup")
    public String signupView(){
        return "member/signup";
    }
}
