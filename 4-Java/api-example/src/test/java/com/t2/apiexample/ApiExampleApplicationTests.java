package com.t2.apiexample;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TESTE BÁSICO DE ARRANQUE DO CONTEXTO SPRING
 *
 * O que este teste valida?
 *  - Se a aplicação Spring Boot CONSEGUE subir o contexto de IoC sem erros.
 *  - Isso inclui: varrer componentes (@Component/@Service/@Repository/@Controller),
 *    processar configurações, criar beans e resolver dependências (@Autowired).
 *
 * Quando ele falha?
 *  - Erro de configuração (ex.: bean faltando, dependência circular).
 *  - Propriedades ausentes/invalidas (application.properties/yaml).
 *  - Migrações de banco (Flyway/Liquibase) com falha ao iniciar.
 *  - Falha de auto-configuração (ex.: conflito de starters).
 *
 * Performance: @SpringBootTest carrega TODO o contexto → mais lento.
 *  - Para testes unitários rápidos, prefira fatiar por slice:
 *      @WebMvcTest (controllers), @DataJpaTest (repositórios), etc.
 *  - Para configurar um perfil de teste isolado:
 *      @ActiveProfiles("test") + application-test.properties
 *  - Para não subir servidor web real:
 *      @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
 *
 * Boas práticas:
 *  - Mantenha este teste como “canário”: se ele falhar, há algo sistêmico.
 *  - Evite lógica aqui; o objetivo é apenas validar que TUDO liga sem exceção.
 */
@SpringBootTest // Sobe o contexto completo da aplicação para o teste
class ApiExampleApplicationTests {

    /**
     * Se o método completar sem lançar exceção, o teste passa.
     * Qualquer falha ao construir o contexto derruba o teste automaticamente.
     *
     * Dica: caso queira garantir que um bean específico exista, você pode
     * injetá-lo com @Autowired no teste — se a injeção falhar, o teste falha.
     */
    @Test
    void contextLoads() {
        // Intencionalmente vazio.
        // O “assert” aqui é implícito: NÃO ter exceções durante a inicialização.
        // Ex.: se quiser tornar explícito, você pode fazer um assert true:
        // Assertions.assertTrue(true);
    }
}