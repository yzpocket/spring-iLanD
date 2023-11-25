package com.yzpocket.iland.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameViewController {
    // 게임 화면
    @GetMapping("/game")
    public String gameView(){
        return "/contents/game";
    }
}
