package com.t2.apiexample.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * CONFIGURAÇÃO DO OPENAPI/ SWAGGER
 *
 * Objetivo:
 *  - Centralizar metadados da sua API (título, versão, descrição, contato)
 *  - Declarar o esquema de segurança (JWT Bearer) para que o Swagger UI
 *    permita inserir o token e testar endpoints protegidos.
 *
 * Onde isso aparece?
 *  - No Swagger UI (ex.: /swagger-ui.html ou /swagger-ui/index.html, dependendo do setup)
 *  - No documento OpenAPI gerado (ex.: /v3/api-docs)
 *
 * Como usar no Swagger UI:
 *  1) Clique em "Authorize"
 *  2) Selecione "bearerAuth"
 *  3) Informe o token como:  Bearer <seu_jwt>
 *     (em muitos UIs, basta informar apenas o token; o prefixo pode ser adicionado automaticamente)
 *
 * Observação:
 *  - As anotações aqui NÃO implementam segurança — elas apenas DOCUMENTAM.
 *  - A segurança real vem da configuração do Spring Security (filtros/JWT).
 */
@Configuration // Indica que esta classe faz parte da configuração do Spring (um bean de configuração)
@OpenAPIDefinition(
        // Metadados gerais exibidos no topo do Swagger UI e no /v3/api-docs
        info = @Info(
                title = "Demonstration API",           // Título amigável da API
                version = "v1",                         // Versão do contrato (ajude consumidores a versionar)
                description = "API de demonstração com autenticação JWT e recursos de usuários, matrículas e cursos.",
                contact = @Contact(                     // Contato para dúvidas/bugs (não precisa ser real em demo)
                        name = "Equipe de Suporte",
                        email = "naoretornamos@nunca.com"
                )
        ),
        // Requisito de segurança padrão aplicado “globalmente”
        // => Endpoints herdam que precisam de 'bearerAuth', a menos que outro requisito os substitua.
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
// Define o ESQUEMA DE SEGURANÇA usado na especificação OpenAPI.
// Isso habilita o botão "Authorize" no Swagger UI, permitindo testar endpoints com JWT.
@SecurityScheme(
        name = "bearerAuth",                // Nome lógico do esquema (referenciado em @SecurityRequirement)
        type = SecuritySchemeType.HTTP,     // Tipo HTTP (em oposição a APIKEY, OAUTH2, OPENIDCONNECT)
        scheme = "bearer",                  // Esquema do header Authorization: "Bearer <token>"
        bearerFormat = "JWT"                // Dica para UIs/clients (formato do token). Não valida nada por si só.
)
public class OpenApiConfig {
    // Classe propositalmente vazia:
    // As anotações de classe já são suficientes para o springdoc-openapi gerar a documentação.
    // Não é necessário adicionar código aqui.
}