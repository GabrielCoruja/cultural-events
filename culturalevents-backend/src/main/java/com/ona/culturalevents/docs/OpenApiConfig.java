package com.ona.culturalevents.docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig implements OpenApiCustomizer {

  @Override
  public void customise(OpenAPI openApi) {
    Info info = new Info()
        .title("Eventos Culturais")
        .description("Este projeto apresenta uma API RESTful que facilita a gestão de listas de"
            + " contatos, possibilitando as pessoas usuárias criar, visualizar, atualizar e excluir"
            + " contatos de forma intuitiva e prática. A API oferece endpoints específicos para"
            + " operações CRUD (Create, Read, Update, Delete) em listas de contatos, visando"
            + " proporcionar uma experiência consistente e confiável.")
        .version("1.0.0");

    openApi.info(info);
  }
}
