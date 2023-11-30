package com.yzpocket.iland.controller.viewController;

import com.yzpocket.iland.dto.NoticeResponseDto;
import com.yzpocket.iland.dto.VideoResponseDto;
import com.yzpocket.iland.service.NoticeService;
import com.yzpocket.iland.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ManagementViewController {
    private final NoticeService noticeService;
    private final VideoService videoService;

    // 관리자 화면
    @GetMapping("/admin")
    public String adminView() {
        return "/admin/employee_main";
    }

    // 공지 관리 화면
    @GetMapping("/management_announcement")
    public String noticeView(@RequestParam(name = "page", defaultValue = "0") int page,
                             @RequestParam(name = "size", defaultValue = "5") int size,
                             Model model){
        Page<NoticeResponseDto> noticePage = noticeService.getAllNotices(page, size);
        model.addAttribute("currentPage", noticePage.getNumber());
        model.addAttribute("totalPages", noticePage.getTotalPages());
        model.addAttribute("notices", noticePage.getContent());
        return "/admin/management_announcement";
    }

    // 컨텐츠 관리 화면
    @GetMapping("/management_contents")
    public String contentView(@RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "5") int size,
                              Model model){
        Page<VideoResponseDto> videoPage = videoService.getAllVideos(page, size);
        model.addAttribute("currentPage", videoPage.getNumber());
        model.addAttribute("totalPages", videoPage.getTotalPages());
        model.addAttribute("notices", videoPage.getContent());
        return "/admin/management_contents";
    }

    // 채팅 관리 화면
    @GetMapping("/management_chatting")
    public String chatView(){
        return "/admin/management_chatting";
    }
}
