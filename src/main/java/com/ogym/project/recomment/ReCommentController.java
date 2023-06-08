package com.ogym.project.recomment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/recomment")
@RequiredArgsConstructor
@Controller
public class ReCommentController {

    private final ReCommentService reCommentService;

}
