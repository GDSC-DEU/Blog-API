package com.gdsc.blog.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateDto {
    @Schema(description = "제목", example = "첫 게시글 입니다.")
    public String subject;

    @Schema(description = "내용", example = "안녕하세요.")
    public String content;
}
