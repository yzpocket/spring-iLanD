package com.yzpocket.iland.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VideoViewController {
    // 비디오 화면
    @GetMapping("/movies&tv")
    public String videoView(){
        return "/contents/movies&tv";
    }

    // 비디오 시청 화면
    @GetMapping("/movies&tv/{videoId}/watch")
    public String videoWatchView(@PathVariable String videoId){

        return "/contents/movies&tvWatch";
    }
}
