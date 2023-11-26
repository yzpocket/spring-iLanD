package com.yzpocket.iland.controller.viewController;

import com.yzpocket.iland.dto.NoticeResponseDto;
import com.yzpocket.iland.service.NoticeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class NoticeViewController {
    private final NoticeService noticeService;

    @GetMapping("/announcement")
    public String getAllNotices(@RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size,
                                Model model) {
        Page<NoticeResponseDto> noticePage = noticeService.getAllNotices(page, size);
        model.addAttribute("currentPage", noticePage.getNumber());
        model.addAttribute("totalPages", noticePage.getTotalPages());
        model.addAttribute("notices", noticePage.getContent());
        return "/contents/announcement";
    }

    @GetMapping("/important")
    public String getImportantNotices(Model model) {
        Page<NoticeResponseDto> importantNotices = noticeService.getImportantNotices();
        model.addAttribute("importantNotices", importantNotices);
        return "/contents/announcement";
    }
}
