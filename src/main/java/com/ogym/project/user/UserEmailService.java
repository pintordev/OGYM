package com.ogym.project.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserEmailService {
    private final JavaMailSender javaMailSender;

    public void mailSend(String email, String type, String code) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(type);
        simpleMailMessage.setText(code);

//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//        String title = "SBB " + type + " 인증 메일입니다.";
//        String content = "<div style='text-align: center'>";
//        content += "<div style='border: 2px solid black; display: inline-block; padding: 10px; text-align: start'>";
//        content += "<span>SBB [" + type + "] 인증번호 입니다.</span><br>";
//        content += "<span>아래 인증 코드를 복사 후 입력해주세요.</span>";
//        content += "<h2 style='color: indigo; text-align: center'>" + code + "</h2>";
//        content += "</div>";
//        content += "</div>";
//
//        try {
//            mimeMessage.setT
//            mimeMessage.setSubject(title, "UTF-8");
//            mimeMessage.setText(content);
//
//
//        } catch {}

        this.javaMailSender.send(simpleMailMessage);
    }
}
