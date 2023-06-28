package com.ogym.project.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/file")
@RequiredArgsConstructor
@Controller
public class FileController {


    public String userProfileUpload() {
        return "";
    }
}
