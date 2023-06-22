package com.ogym.project.user.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogym.project.DataNotFoundException;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final UserEmailService userEmailService;
    private final HttpSession httpSession; //


    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        System.out.println("들어옴");

        System.out.println("loginId = " + userCreateForm.getLoginId());
        System.out.println("password = " + userCreateForm.getPassword());
        System.out.println("passwordCheck = " + userCreateForm.getPasswordCheck());
        System.out.println("nickname = " + userCreateForm.getNickname());
        System.out.println("username = " + userCreateForm.getUsername());
        System.out.println("phone = " + userCreateForm.getPhone());
        System.out.println("birthYear = " + userCreateForm.getBirthYear());
        System.out.println("birthMonth = " + userCreateForm.getBirthMonth());
        System.out.println("birthDay = " + userCreateForm.getBirthDay());
        System.out.println("email = " + userCreateForm.getEmail());
        System.out.println("code = " + userCreateForm.getCode());
        System.out.println("genCode = " + userCreateForm.getGenCode());

        if (bindingResult.hasErrors()) {
            for (int i = 0; i < bindingResult.getErrorCount(); i++) {
                System.out.println(bindingResult.getAllErrors().get(i));
            }
            return "signup_form";
        }

        // 비밀번호와 비밀번호 확인에 입력한 문자열이 서로 다르면 다시 입력 하도록
        if (!userCreateForm.getPassword().equals(userCreateForm.getPasswordCheck())) {
            System.out.println("password confirm error");
            bindingResult.rejectValue("passwordCheck", "passwordInCorrect",
                    "입력한 비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        if (!this.userService.confirmCertificationCode(userCreateForm.getCode(), userCreateForm.getGenCode())) {
            System.out.println("code confirm error");
            bindingResult.rejectValue("code", "codeInCorrect",
                    "입력한 인증번호가 일치하지 않습니다.");
            return "signup_form";
        }

        System.out.println("validation all completed");

        userService.create(userCreateForm.getLoginId(), userCreateForm.getPassword(), userCreateForm.getNickname(), userCreateForm.getUsername(), userCreateForm.getPhone(), userCreateForm.getBirthYear(),
                userCreateForm.getBirthMonth(), userCreateForm.getBirthDay(), userCreateForm.getEmail());

        return "redirect:/user/login";
    }

    @PostMapping("/signup/emailConfirm")
    @ResponseBody
    public String emailConfirm(@RequestParam("email") String email) {
        String genCode = this.userService.genConfirmCode(8);
        System.out.println(genCode);
        this.userEmailService.mailSend(email, "이메일 인증", genCode);
        return this.userService.getEmailConfirmCode(genCode);
    }


    @GetMapping("/signup/code")
    @ResponseBody
    public String genCodeCheck(@RequestParam("genCode") String genCode, @RequestParam("code") String code) {
        if(this.userService.confirmCertificationCode(code, genCode)) {
            return "success";
        } else {
            throw new RuntimeException("인증코드가 일치하지 않습니다.");
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/find")
    public String findUser() {
        return "user_find";
    }


    //아이디찾기, 비밀번호 찾기 둘다 쓸 수 있도록 만듬
    @PostMapping("/find")
    @ResponseBody
    //프론트에서 받아온 email, loginId 값을 담아온다
    public String findUser(@RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "loginId", required = false) String loginId) {
        try {
            //아이디찾기 loginId만 입력됐을때 없는경우 아이디만 반환하도록 진행
            if (email != null && loginId == null) {
                SiteUser user = userService.getUserByEmail(email);
                return user.getLoginId();
            //비밀번호찾기 email, loginId 둘다 값이 있을 경우 로직
            } else if (email != null && loginId != null) {
                SiteUser user = userService.getUserByLoginAndEmail(loginId, email);
                if (user != null) {
                    String tempPassword = userService.genConfirmCode(8);
                    //임시 비밀번호 발송, 이후 기존 비밀번호를 임시비밀번호로 교체하는것도 추가해야함
                    this.userEmailService.mailSend(email, "임시 비밀번호 발송", tempPassword);
                    System.out.println(tempPassword);
                    this.userService.modifyPassword(tempPassword, user);
                    return "임시 비밀번호를 이메일로 발송했습니다.";
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } catch (DataNotFoundException e) {
            return "";
        }
    }



//    @PostMapping("/find")
//    @ResponseBody
//    public String findUserPassword(@RequestParam("loginId") String loginId, @RequestParam("email") String email) {
//        try {
//            SiteUser user = userService.getUserByLoginId(loginId);
//            if (user.getEmail().equals(email)) {
//                String password = user.getPassword();
//                // 이메일 발송 로직 구현
//                sendEmailWithPassword(email, password);
//                return "비밀번호를 이메일로 발송했습니다.";
//            } else {
//                return "입력한 정보가 일치하지 않습니다.";
//            }
//        } catch (DataNotFoundException e) {
//            return "가입된 정보가 없습니다.";
//        }
//    }
//@GetMapping("/")
//public String index(Model model) {
//    SessionUser user = (SessionUser) httpSession.getAttribute("user");
//    if (user != null) {
//        model.addAttribute("userName", user.getName());
//    }
//    return "index";
//}


}
