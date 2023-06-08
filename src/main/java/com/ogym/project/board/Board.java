package com.ogym.project.board;

import com.ogym.project.boardCategory.BoardCategory;
import com.ogym.project.comment.Comment;
import com.ogym.project.user.SiteUser;
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
    private BoardCategory boardCategory;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @ManyToMany
    private Set<SiteUser> voter;

    private Integer hit;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}
