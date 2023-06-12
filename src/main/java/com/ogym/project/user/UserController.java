package com.ogym.project.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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


    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup";
    }

    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        // 비밀번호와 비밀번호 확인에 입력한 문자열이 서로 다르면 다시 입력 하도록
        if (!userCreateForm.getPassword().equals(userCreateForm.getPasswordCheck())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "입력한 비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        if (!this.userService.confirmCertificationCode(userCreateForm.getCode(), userCreateForm.getGenCode())) {
            bindingResult.rejectValue("inputCode", "codeInCorrect",
                    "입력한 인증번호가 일치하지 않습니다.");
            return "signup_form";
        }

        userService.create(userCreateForm.getLoginId(), userCreateForm.getPassword(), userCreateForm.getEmail(), userCreateForm.getName(), userCreateForm.getBirthYear(),
                userCreateForm.getBirthMonth(),userCreateForm.getBirthDay(),userCreateForm.getPhone());

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



    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/find")
    public String findUser() {
        return "user_find";
    }

    //---------카카오 로그인 인증
    @ResponseBody
    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) {
        //post방식으로 key=value 데이터를 요청(카카오쪽으로)
        //Retrofit2 해당라이버르러리는 안드로이드에서 주로사용
        //OkHttp
        //RestTemplate
        RestTemplate rt = new RestTemplate();//RestTemplate  라이브러리란 http요청을 편하게 사용할수있는 라이브러리

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");//key는 grant_type타입 값은 authorization_code 로  grant_type=authorization_code 로 보면된다.
        params.add("client_id", "d242caacaeef4e7e50acc0b0df1bec34");
        params.add("redirect_uri", "http://localhost:14641/user/auth/kakao/callback");
        params.add("code", code);
        //HttpHEADER와 HttpBody를 하나의 오브젝트에담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);//body data와 헤더의 데이터의 Entyity가 된다.
        //Http 요청하기 - Post방식으로 - 그리고response 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange( // 제네릭으로 엔티티에 String 을 사용
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,//httpbody의 들어갈 데이터와 http의 헤더값
                String.class //응답을 받을 타입이  String로 위에 response 응답이 String 데이터로 받는다

        );
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ;
        }
        System.out.println("카카오 엑세스 토큰" + oAuthToken.getAccess_token());


        //post방식으로 key=value 데이터를 요청(카카오쪽으로)
        //Retrofit2 해당라이버르러리는 안드로이드에서 주로사용
        //OkHttp
        //RestTemplate
        RestTemplate rt2 = new RestTemplate();//RestTemplate  라이브러리란 http요청을 편하게 사용할수있는 라이브러리

        //HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHEADER와 HttpBody를 하나의 오브젝트에담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);//body data와 헤더의 데이터의 Entyity가 된다.
        //Http 요청하기 - Post방식으로 - 그리고response 변수의 응답 받음
        ResponseEntity<String> response2 = rt2.exchange( // 제네릭으로 엔티티에 String 을 사용
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,//httpbody의 들어갈 데이터와 http의 헤더값
                String.class
        );//응답을 받을 타입이  String로 위에 response 응답이 String 데이터로 받는다

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ;
        }
        // kakaoProfile이 null인 경우에 대한 처리
        UUID garbagePassword = null;
        if (kakaoProfile != null) {
            System.out.println("카카오 아이디 (번호):" + kakaoProfile.getId());
            System.out.println("카카오 이메일" + kakaoProfile.getKakao_account().getEmail());
            System.out.println("블로그 유저네임:" + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
            System.out.println("블로그서버 이메일" + kakaoProfile.getKakao_account().getHas_Email());
            //해당 UUID garbagePassword =UUID.randomUUID();은 임시 패스워드
            garbagePassword = UUID.randomUUID();
            System.out.println("블로그서버 패스워드" + garbagePassword);
        } else {



        }
        return response2.getBody();

    }
}
