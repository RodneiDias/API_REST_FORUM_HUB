package br.com.alura.forum.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpingDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info().title("API-REST Forum Hub")
                        .version("v1")
                        .description("O projeto é uma API REST para gerenciamento de um fórum de discussões, construída com JAVA e Spring Boot. Ela permite que os usuários façam autenticação, criem cursos, postem tópicos respostas referentes a cada curso e visualizem cusos, tópicos e respostas. A aplicação usa Spring Security para autenticação e autorização, Hibernate para mapeamento objeto-relacional, Flyway para migrações de banco de dados e Springdoc OpenAPI para documentação da API.")
                        .contact(new Contact()
                                .name("Rodnei Ferreira")
                                .email("rodnei.dias04@gmail.com")
                                .url("https://forum.hub"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}