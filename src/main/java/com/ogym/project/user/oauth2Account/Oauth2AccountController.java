package com.ogym.project.user.oauth2Account;

import com.ogym.project.user.user.SiteUser;
import com.ogym.project.user.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Slf4j
@RequestMapping("/user/oauth2")
@RequiredArgsConstructor
@Controller
public class Oauth2AccountController {

    private final Oauth2AccountService oauth2AccountService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated")
    @GetMapping("/{socialType}")
    public void requestCode(@PathVariable("socialType") String socialType, HttpServletResponse response) {

        String redirectUri = this.oauth2AccountService.getRedirectUrlCode(socialType);

        log.info(socialType + "redirectUri: " + redirectUri);

        try {
            response.sendRedirect(redirectUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreAuthorize("isAuthenticated")
    @GetMapping("/code/{socialType}")
    public String createOauth2Account(@PathVariable("socialType") String socialType,
                                      @RequestParam(value = "code") String code, @RequestParam(value = "state", defaultValue = "") String state,
                                      Principal principal) {

        log.info(socialType + "code: " + code);
        log.info(socialType + "state: " + state);

        String accessToken = this.oauth2AccountService.getAccessToken(socialType, code, state);

        log.info(socialType + "accessToken: " + accessToken);

        Map<String, Object> userAttributes = this.oauth2AccountService.getUserAttribute(socialType, accessToken);

        log.info(socialType + "userAttributes: " + userAttributes);

        SiteUser parent = this.userService.getUserByLoginId(principal.getName());
        String provider = (String) userAttributes.get("provider");
        String providerId = (String) userAttributes.get("providerId");
        String email = (String) userAttributes.get("email");
        String name = (String) userAttributes.get("name");

        this.oauth2AccountService.create(provider, providerId, email, name, parent);

        return "redirect:/user/mypage";
    }
}
