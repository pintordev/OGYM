package com.ogym.project.recomment;

import com.ogym.project.board.Board;
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
public class ReComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private SiteUser author;

    @ManyToOne
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ReComment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ReComment> children;

    @ManyToMany
    private Set<SiteUser> voter;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}
