package com.gdsc.blog.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ArticleUpdateDto {
    @Schema(description = "수정할 제목", example = "수정된 게시글 제목")
    public String title;

    @Schema(description = "수정할 내용", example = "수정된 게시글 내용")
    public String content;
}
