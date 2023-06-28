package com.ogym.project.file;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

import static org.thymeleaf.util.StringUtils.concat;

@RequestMapping("/file")
@RequiredArgsConstructor
@Controller
public class FileController {

    private final String presentPath = System.getProperty("user.dir")
            + File.separator + "upload";

    @GetMapping("/userProfileUpload")
    public String userProfileUpload() {

        System.out.println("현재 작업 경로: " + presentPath);

//        File file = new File();
//        file.mkdir();

        return "file";
    }
}
