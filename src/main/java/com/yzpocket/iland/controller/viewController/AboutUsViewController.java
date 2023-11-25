package com.yzpocket.iland.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsViewController {
    // 정보 화면
    @GetMapping("/aboutus_page")
    public String aboutUsView(){
        return "/info/aboutus_page";
    }
}
