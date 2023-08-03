package com.ogym.project.user.user;

import com.ogym.project.CommonUtil;
import com.ogym.project.DataNotFoundException;

import com.ogym.project.board.board.Board;
import com.ogym.project.board.board.BoardService;
import com.ogym.project.board.comment.Comment;
import com.ogym.project.board.comment.CommentService;
import com.ogym.project.board.reComment.ReComment;
import com.ogym.project.board.reComment.ReCommentService;
import com.ogym.project.user.oauth2Account.Oauth2Account;
import groovyjarjarpicocli.CommandLine;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final UserEmailService userEmailService;
    private final HttpSession httpSession; //12644
    private final BoardService boardService;
    private final CommentService commentService;
    private final ReCommentService reCommentService;

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
        System.out.println("zoneCode = " + userCreateForm.getZoneCode());
        System.out.println("mainAddress = " + userCreateForm.getMainAddress());
        System.out.println("subAddress = " + userCreateForm.getSubAddress());
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
                userCreateForm.getBirthMonth(), userCreateForm.getBirthDay(), userCreateForm.getEmail(), userCreateForm.getZoneCode(), userCreateForm.getMainAddress(), userCreateForm.getSubAddress());

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
        this.userEmailService.mailSend(email, "이메일 인증", "인증 코드", genCode);
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
    public String login(HttpServletRequest request, Model model,
                        @RequestParam(value = "error", defaultValue = "") String error,
                        @RequestParam(value = "exception", defaultValue = "") String exception) {
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }
        return "login";
    }

    @GetMapping("/login/authenticate")
    @ResponseBody
    public String idAndPasswordAuthenticate(@RequestParam("loginId") String loginId, @RequestParam("password") String  password) {

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
                    this.userEmailService.mailSend(email, "임시 비밀번호 발송", "임시 비밀번호",  tempPassword);
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
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/password")
    public String modifyPassword(UserPasswordForm userPasswordForm) {
        return "modify_password_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/password")
    public String modifyPassword(@Valid UserPasswordForm userPasswordForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "modify_password_form";
        }

        SiteUser user = this.userService.getUser(principal.getName());
        if (!this.userService.confirmPassword(userPasswordForm.getPresentPW(), user)) {
            bindingResult.rejectValue("presentPW", "passwordInCorrect",
                    "현재 비밀번호를 바르게 입력해주세요.");
            return "modify_password_form";
        }

        // 비밀번호와 비밀번호 확인에 입력한 문자열이 서로 다르면 다시 입력 하도록
        if (!userPasswordForm.getNewPW1().equals(userPasswordForm.getNewPW2())) {
            bindingResult.rejectValue("newPW2", "passwordInCorrect",
                    "입력한 비밀번호가 일치하지 않습니다.");
            return "modify_password_form";
        }

        userService.modifyPassword(userPasswordForm.getNewPW1(), user);

        return "redirect:/user/logout";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/nickname")
    public  String modifyNickname() {return "modify_nickname_form"; }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/nickname")
    public String modifyNickname(@RequestParam("newNickname") String newNickname, Principal principal) {
        SiteUser user = this.userService.getUser(principal.getName());
        System.out.println(newNickname);

        if (newNickname.matches(".*[ㄱ-ㅎㅏ-ㅣ!\"#$%&'()*+,-./:;<=>?@\\[\\\\\\]^_`{|}~\\s]+.*")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용할 수 없는 닉네임입니다.");
        }

        if (!this.userService.isNickNameDuplicate(newNickname)) {
            // 닉네임 변경 로직 추가
            user.setNickname(newNickname);
            this.userService.modifyNickname(newNickname, user);

            return "redirect:/user/mypage";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용중인 닉네임입니다.");
        }
    }


    @GetMapping("/modify/nickname-check")
    @ResponseBody
    public String modifyNicknameCheck(@RequestParam("newNickname") String newNickname, Principal principal) {

        SiteUser user = this.userService.getUser(principal.getName());
        if (newNickname.matches(".*[ㄱ-ㅎㅏ-ㅣ!\"#$%&'()*+,-./:;<=>?@\\[\\\\\\]^_`{|}~\\s]+.*")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용할 수 없는 닉네임입니다.");
        }

        if (!this.userService.isNickNameDuplicate(newNickname)) {
            return "사용 가능한 닉네임입니다.";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용중인 닉네임입니다.");
        }
    }

    @GetMapping("/mypage")
    public String myPage(Model model, Principal principal,
                         @RequestParam(value = "section", defaultValue = "information") String section,
                         @RequestParam(value = "bPage", defaultValue = "0") int bPage,
                         @RequestParam(value = "cPage", defaultValue = "0") int cPage,
                         @RequestParam(value = "rPage", defaultValue = "0") int rPage) {

        SiteUser user = this.userService.getUserByLoginId(principal.getName());
        model.addAttribute("user", user);

        List<String> socialLinked = new ArrayList<>();
        for (Oauth2Account socialAccount : user.getSocialAccount()) {
            socialLinked.add(socialAccount.getProvider());
        }
        model.addAttribute("socialLinked", socialLinked);

        Page<Board> boardPaging = this.boardService.getListByUser(bPage, user);
        model.addAttribute("boardPaging", boardPaging);

        Page<Comment> commentPaging = this.commentService.getListByUser(cPage, user);
        model.addAttribute("commentPaging", commentPaging);

        Page<ReComment> reCommentPaging = this.reCommentService.getListByUser(rPage, user);
        model.addAttribute("reCommentPaging", reCommentPaging);

        model.addAttribute("section", section);
        model.addAttribute("bPage", bPage);
        model.addAttribute("cPage", cPage);
        model.addAttribute("rPage", rPage);

        return "my_page";
    }


}