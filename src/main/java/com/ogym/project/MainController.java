package com.ogym.project;

import com.ogym.project.board.board.Board;
import com.ogym.project.board.board.BoardService;
import com.ogym.project.user.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final BoardService boardService;

    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Board> bestBoardList = this.boardService.getBestBoard();
        model.addAttribute("bestBoardList", bestBoardList);
        List<Board> recentBoardList = this.boardService.getRecentBoard();
        model.addAttribute("recentBoardList", recentBoardList);

        String presentPath = System.getProperty("user.dir");
        System.out.println("현재 파일 경로: " + presentPath);

        return "index";
    }


}

