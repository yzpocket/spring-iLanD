package com.yzpocket.iland.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {
    // 메인 화면
    @GetMapping("/")
    public String mainView(){
        return "iLanD_main";
    }
}
