package com.yzpocket.iland.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VideoViewController {
    // 비디오 화면
    @GetMapping("/movies&tv")
    public String videoView(){
        return "/contents/movies&tv";
    }
}
