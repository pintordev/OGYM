package com.ogym.project.user.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@RequiredArgsConstructor
@Service
public class UserEmailService {
    private final JavaMailSender javaMailSender;

    public void mailSend(String email, String type, String type2, String code) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String title = "oGym " + type + " 메일입니다.";
            String content = "<div style='text-align: center'>";
            content += "<div style='border: 2px solid black; display: inline-block; padding: 10px; text-align: start'>";
            content += "<span>oGym [" + type2 + "] 입니다.</span><br>";
            content += "<span>아래 " + type2 + "를 입력해주세요.</span>";
            content += "<h2 style='color: indigo; text-align: center'>" + code + "</h2>";
            content += "</div>";
            content += "</div>";

            helper.setTo(email);
            helper.setSubject(title);
            helper.setText(content, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // 예외 처리
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // JavaMailSender 구현체를 생성하고 필요한 정보를 설정
        JavaMailSender javaMailSender = createJavaMailSender();

        // UserEmailService 인스턴스 생성
        UserEmailService userEmailService = new UserEmailService(javaMailSender);

        // HTML 형식의 이메일 전송
        String email = "example@example.com";
        String type = "이메일 인증";
        String code = "ABC123"; // 이메일 인증 코드

        String type2="";
        userEmailService.mailSend(email, type, type2, code);
    }

    // JavaMailSender 구현체 생성
    private static JavaMailSender createJavaMailSender() {
        // JavaMailSenderImpl 인스턴스 생성
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // 필요한 정보 설정
        mailSender.setHost("your-mail-server-host");
        mailSender.setPort(587);
        mailSender.setUsername("your-username");
        mailSender.setPassword("your-password");

        // 추가적인 설정 (옵션)
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }
}
