package com.yzpocket.iland.controller.viewController;

import com.yzpocket.iland.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VideoViewController {
    private VideoService videoService;

    // 비디오 화면
    @GetMapping("/movies&tv")
    public String videoView(){
        return "/contents/movies&tv";
    }

    // 비디오 시청 화면
    @GetMapping("/movies&tv/{videoId}/watch")
    public String videoWatchView(@PathVariable String videoId, Model model){
        model.addAttribute("videoId", videoId);
        return "/contents/movies&tvWatch";
    }
}
