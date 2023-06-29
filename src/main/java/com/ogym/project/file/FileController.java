package com.ogym.project.file;

import com.ogym.project.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.thymeleaf.util.StringUtils.concat;

@Slf4j
@RequestMapping("/file")
@RequiredArgsConstructor
@Controller
public class FileController {

    private final FileService fileService;

    @GetMapping("")
    public String upload(Model model, FileForm fileForm) {
        UploadedFile file = this.fileService.getFile(4L);
        String path = this.fileService.getFilePath(file);
        model.addAttribute("path", path);
        return "file";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@Valid FileForm fileForm, BindingResult bindingResult) throws IOException {
        MultipartFile file = fileForm.getFile();
        UploadedFile uploadedFile = this.fileService.upload(file, "user", "profile", "pintor");
        return this.fileService.getFilePath(uploadedFile);
    }
}
