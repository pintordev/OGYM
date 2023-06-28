package com.ogym.project.file;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;

import static org.thymeleaf.util.StringUtils.concat;

@RequestMapping("/file")
@RequiredArgsConstructor
@Controller
public class FileController {

    private final String presentPath = System.getProperty("user.dir") + File.separator + "file";

    @PostMapping("/upload/{type}")
    public String upload(@RequestParam MultipartFile file, Principal principal) {

        String saveProfilePath = presentPath
                + File.separator + "user"
                + File.separator + "profile";

        String saveThumbnailPath = presentPath
                + File.separator + "user"
                + File.separator + "thumbnail";

        // 파일명 규칙
        // loginID_type_createDate


//        File file = new File();
//        file.mkdir();

        return "file";
    }
}
