package com.gdsc.blog.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@OpenAPIDefinition(info = @Info(title = "JSON Web Token Authentication API", version = "1.0",
        description = "JSON Web Token Authentication API Documentation",
        contact = @Contact(name = "Kim namju", email = "cmsong111@naver.com")))

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        // SecuritySecheme명
        String jwtSchemeName = "jwtAuth";

        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        // API 문서에 적용할 SecurityScheme
        Components components = new Components().addSecuritySchemes(jwtSchemeName,
                new SecurityScheme().name(jwtSchemeName).type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer").bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

        return new OpenAPI().addSecurityItem(securityRequirement).components(components);

    }
}


