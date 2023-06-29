package com.ogym.project.inquiry;

import com.ogym.project.user.user.SiteUser;
import com.ogym.project.user.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/inquiry")
@RequiredArgsConstructor
@Controller
public class InquiryController {

    private final InquiryService inquiryService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public String main(Model model) {
        return "inquiry_main";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable("id") Long id, Principal principal) {

        Inquiry inquiry = this.inquiryService.getInquiry(id);

        if (!inquiry.getInquirer().getLoginId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "접근 권한이 없습니다.");
        }

        model.addAttribute("inquiry", inquiry);

        return "inquiry_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String writeInquiry(Model model, InquiryForm inquiryForm) {
        return "inquiry_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String writeInquiry(Model model,
                               @Valid InquiryForm inquiryForm, BindingResult bindingResult,
                               Principal principal) {

        if (bindingResult.hasErrors()) {
            return "inquiry_form";
        }

        SiteUser inquirer = this.userService.getUserByLoginId(principal.getName());
        Inquiry inquiry = this.inquiryService.create(inquiryForm.getTitle(), inquiryForm.getContent(), inquirer);

        return String.format("redirect:/inquiry/%s", inquiry.getId());
    }
}
