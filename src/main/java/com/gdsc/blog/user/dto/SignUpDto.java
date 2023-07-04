package com.gdsc.blog.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {
	@Schema(description = "이메일", example = "test@test.com")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
			message = "이메일 형식이 올바르지 않습니다.")
	private String email;

	@Schema(description = "비밀번호", example = "1234")
	private String password;

	@Schema(description = "이름", example = "홍길동")
	private String username;
}
