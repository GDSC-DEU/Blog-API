package com.gdsc.blog.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentUpdateDto {
    @Schema(description = "수정할 내용", example = "수정된 댓글 내용")
    public String content;
}
