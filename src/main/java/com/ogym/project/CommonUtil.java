package com.ogym.project;

import com.ogym.project.user.oauth2Account.Oauth2Account;
import com.ogym.project.user.user.SiteUser;
import com.ogym.project.user.user.UserService;
import lombok.RequiredArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CommonUtil {

    private final UserService userService;

    public String markdown(String markdown) {

        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        return renderer.render(document);
    }

    public String timeDifference(LocalDateTime oldDate) {

        Duration diff = Duration.between(oldDate, LocalDateTime.now());

        if (diff.getSeconds() < 60) return "방금 전";
        if (diff.getSeconds() < 60 * 60) return diff.toMinutes() + "분 전";
        if (diff.getSeconds() < 60 * 60 * 24) return diff.toHours() + "시간 전";
        if (diff.getSeconds() < 60 * 60 * 24 * 7) return diff.toDays() + "일 전";
        return oldDate.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
    }

    public SiteUser usernameToUser(String loginId) {
        return this.userService.getUserByLoginId(loginId);
    }

    public String reduceAddress(String address) {
        String[] addressBits = address.split("\s");
        return String.format("%s %s", addressBits[0], addressBits[1]);
    }
}
