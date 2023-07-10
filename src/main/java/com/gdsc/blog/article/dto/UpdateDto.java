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
public class UpdateDto {
    @Schema(description = "제목", example = "수정될 제목")
    public String subject;

    @Schema(description = "내용", example = "수정될 내용")
    public String content;
}