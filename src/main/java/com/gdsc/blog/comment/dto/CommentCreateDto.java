package com.gdsc.blog.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentCreateDto {
    @Schema(description = "내용", example = "댓글 내용")
    public String content;
}
