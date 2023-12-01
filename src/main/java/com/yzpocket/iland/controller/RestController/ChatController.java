package com.yzpocket.iland.controller.RestController;

import com.yzpocket.iland.dto.ChatMessageDto;
import com.yzpocket.iland.entity.ChattingContent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {
    @MessageMapping("/chat")
    @SendTo("/chatMessage")
    public ChattingContent chat(ChatMessageDto message) throws Exception {
        return new ChattingContent( HtmlUtils.htmlEscape(message.getChattingMessage()) );
    }
}
