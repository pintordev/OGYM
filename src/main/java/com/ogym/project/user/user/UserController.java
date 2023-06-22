package com.ogym.project.user.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogym.project.DataNotFoundException;
import com.ogym.project.user.kakao.KakaoProfile;
import com.ogym.project.user.kakao.OAuthToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.UUID;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final UserEmailService userEmailService;


    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
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

        //RedirectAttributes는 리다이렉트 후에도 데이터를 전달하고 유지하기 위함.
        //addFalshAttrribute메서드를 사용하여 데이터를 추가하면, 리다이렉트 이후에도 데이터가 유지되며 한 번만 사용할 수 있어 해당 코드 사용
        //다른걸로는 addAttribute로 전달한 값은 url뒤에  붙으며, 리프레시해도 데이터가 유지되는데 휘발성을 생각해 addFalshAttrribute메서드 사용
        redirectAttributes.addFlashAttribute("signupSuccess", true);
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

    @GetMapping("/signup/loginId")
    @ResponseBody
    public String loginIdDuplicate(@RequestParam("loginId") String loginId) {
        System.out.println(loginId);

        if (loginId.matches("\\s*") || loginId.matches("[ㄱ-ㅎㅏ-ㅣ가-힣]+")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용할 수 없는 아이디입니다.");
        }

        if (!this.userService.isLoginIdDuplicate(loginId)) {
            return "사용 가능한 아이디입니다.";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용중인 아이디입니다.");
        }
    }

    @GetMapping("/signup/nickname")
    @ResponseBody
    public String nicknameDuplicate(@RequestParam("nickname") String nickname) {
        System.out.println(nickname);

        if (nickname.matches("\\s*") || nickname.matches("[ㄱ-ㅎㅏ-ㅣ]+")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용할 수 없는 닉네임입니다.");
        }

        if (!this.userService.isNickNameDuplicate(nickname)) {
            return "사용 가능한 닉네임입니다.";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용중인 닉네임입니다.");
        }
    }

    @GetMapping("/signup/email")
    @ResponseBody
    public String emailDuplicate(@RequestParam("email") String email) {
        System.out.println(email);

        if (!this.userService.isEmailDuplicate(email)) {
            return "사용 가능한 이메일입니다.";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용중인 이메일입니다.");
        }
    }

    @GetMapping("/signup/phoneNumber")
    @ResponseBody
    public String phoneNumberDuplicate(@RequestParam("phone") String phoneNumber) {
        System.out.println(phoneNumber);

        if (!this.userService.isPhoneNumberDuplicate(phoneNumber)) {
            return "사용 가능한 번호입니다.";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용중인 번호입니다.");
        }
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

    @GetMapping("/login/authenticate")
    @ResponseBody
    public String idAndPasswordAuthenticate(@RequestParam("loginId") String loginId, @RequestParam("password") String  password) {
        System.out.println(loginId);
        System.out.println(password);
        System.out.println();

        if (this.userService.authenticateLoginIdAndPassword(loginId, password)) {
            return "";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디, 비밀번호를 다시 확인해주세요");
        }
    }

    @GetMapping("/log/loginDate")
    public String loginDate(Principal principal) {
        SiteUser user = this.userService.getUser(principal.getName());
        this.userService.updateLoginDate(user);
        return "redirect:/";
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



}
