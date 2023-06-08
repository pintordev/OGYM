package com.ogym.project;

import com.ogym.project.user.SiteUser;
import com.ogym.project.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final UserService userService;

    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        SiteUser user = this.userService.getUserByLoginId("ogym_admin");
        System.out.println(user.getAuthority().getValue());
        return "index";
    }

}
