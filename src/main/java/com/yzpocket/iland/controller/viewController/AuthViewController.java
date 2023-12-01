package com.yzpocket.iland.controller.viewController;

import com.yzpocket.iland.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {
    // 로그인화면
    @GetMapping("/login")
    public String loginView(Model model) {
        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        Object principal = securityContextHolder.getAuthentication().getPrincipal();
        String role = "";
        if(principal instanceof UserDetailsImpl)
            role = String.valueOf(((UserDetailsImpl) principal).getUser().getRole());
        else
            role = "ANONYMOUS";

        model.addAttribute("loginUserRole", role);

        return "member/employee_login";
    }
}
