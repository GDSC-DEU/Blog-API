package com.gdsc.blog.article.dto;

import com.gdsc.blog.comment.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class ArticleDto {
    private Long idx;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String username;
    private List<Comment> comments;
}
