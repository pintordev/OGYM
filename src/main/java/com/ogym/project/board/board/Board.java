package com.ogym.project.board.board;

import com.ogym.project.board.category.Category;
import com.ogym.project.board.comment.Comment;
import com.ogym.project.user.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private SiteUser author;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @ManyToMany
    private Set<SiteUser> voter;

    private Integer hit;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}
