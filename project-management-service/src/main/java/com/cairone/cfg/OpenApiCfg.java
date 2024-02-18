package com.cairone.cfg;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiCfg {

    @Bean
    public OpenAPI getOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("http", getSecuritySchemeHttpType())
                        .addSecuritySchemes("oauth", getSecuritySchemeOauth2Type())
                )
                .security(Arrays.asList(
                        new SecurityRequirement().addList("http"),
                        new SecurityRequirement().addList("oauth")))
                .info(new Info()
                        .title("Employees Manager service")
                );
    }

    public SecurityScheme getSecuritySchemeHttpType() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");
    }

    private SecurityScheme getSecuritySchemeOauth2Type() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .description("Keycloak")
                .flows(new OAuthFlows()
                        .authorizationCode(new OAuthFlow()
                                .authorizationUrl(authorizationUrl)
                                .tokenUrl(tokenUrl)
                                .scopes(new Scopes()
                                        .addString("profile", "profile")
                                        .addString("email", "email"))
                        )
                );
    }

    @Value("${app.security.idp.authorization-endpoint}")
    private String authorizationUrl;
    @Value("${app.security.idp.token-endpoint}")
    private String tokenUrl;
}
