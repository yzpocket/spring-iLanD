package com.yzpocket.iland.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiceGuideViewController {
    // 가이드정보 화면
    @GetMapping("/service_guide")
    public String gameView(){
        return "info/service_guide";
    }
}
