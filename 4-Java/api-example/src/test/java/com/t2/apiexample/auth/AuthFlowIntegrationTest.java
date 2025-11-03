package com.t2.apiexample.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2.apiexample.auth.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * TESTE DE INTEGRAÇÃO DO FLUXO DE AUTENTICAÇÃO
 *
 * Objetivo: validar ponta a ponta o cenário
 *  1) Registrar um usuário (/auth/register)
 *  2) Ler o token retornado
 *  3) Acessar um endpoint protegido (/users/me) com Bearer Token
 *
 * Por que é "integração"?
 * - Porque sobe o contexto real do Spring (controllers, filters, segurança, etc.)
 * - Exercita a pilha HTTP (sem abrir porta) via MockMvc.
 */
@SpringBootTest                 // Sobe todo o contexto Spring para testes (beans reais)
@AutoConfigureMockMvc           // Configura e injeta automaticamente o MockMvc
public class AuthFlowIntegrationTest {

    @Autowired
    MockMvc mockMvc;            // Mock de camada web: permite "fazer requisições" sem servidor externo

    @Autowired
    ObjectMapper objectMapper;  // Serializa/ desserializa JSON (Jackson) — evita construir JSON "na mão"

    /**
     * Cenário completo: registrar, obter token e acessar recurso protegido.
     *
     * Dica prática:
     * - Em ambientes com restrição de "email único", pode ser útil gerar username aleatório
     *   para que o teste não quebre ao rodar mais de uma vez. Aqui mantemos fixo por simplicidade.
     */
    @Test
    void register_login_and_acess_protected_resource() throws Exception {
        // Monta o payload do registro. Esta classe deve refletir o DTO esperado pelo controller.
        RegisterRequest register = new RegisterRequest();
        register.setName("Test User");
        register.setUsername("test@example.com"); // Se a tabela exigir unicidade, este valor pode precisar variar a cada execução
        register.setPassword("<PASSWORD>");       // Evite senha real; em geral, qualquer string atende no cenário de teste

        // 1) CHAMADA: POST /auth/register
        // - Enviamos JSON com name/username/password
        // - Esperamos 201 Created e um corpo com {"token": "..."}
        MvcResult registerResult = mockMvc.perform(
                    MockMvcRequestBuilders.post("/auth/register")         // endpoint que cria usuário + token
                        .contentType(MediaType.APPLICATION_JSON)          // informamos que o corpo é JSON
                        .content(objectMapper.writeValueAsString(register)) // serializamos o DTO para JSON
                )
                .andExpect(status().isCreated())          // valida HTTP 201 (Created)
                .andExpect(jsonPath("$.token").exists())  // valida que a chave "token" existe no JSON de resposta
                .andReturn();                             // capturamos a resposta para uso posterior (extrair token)

        // 2) EXTRAIR O TOKEN DO REGISTRO
        // - Lemos o corpo da resposta como String e navegamos no JSON com ObjectMapper
        String registerToken = objectMapper
                .readTree(registerResult.getResponse().getContentAsString())
                .get("token")
                .asText();

        // Assertiva com AssertJ: garante que o token não está vazio/nem só espaços
        assertThat(registerToken).isNotBlank();

        // Observação: abaixo há redundância (extrai o token novamente).
        // Mantemos para preservar a intenção original, mas poderíamos reutilizar "registerToken".
        String tokenFromRegister = objectMapper
                .readTree(registerResult.getResponse().getContentAsString())
                .get("token")
                .asText();

        // 3) CHAMADA: GET /users/me COM BEARER TOKEN
        // - Endpoint protegido que deve retornar os dados do usuário autenticado.
        // - O cabeçalho "Authorization: Bearer <token>" é o que habilita o Security a identificar o usuário.
        MvcResult meResult = mockMvc.perform(
                    get("/users/me")
                        .header("Authorization", "Bearer " + tokenFromRegister) // adiciona o JWT no header
                )
                // Dica: poderíamos encadear .andExpect(status().isOk()) também;
                // aqui optamos por coletar o resultado e imprimir no console.
                .andReturn();

        // Logs de apoio ao diagnóstico (aparecem no output do teste)
        System.out.println("DEBUG: /users/me status: " + meResult.getResponse().getStatus());
        System.out.println("DEBUG: /users/me body: " + meResult.getResponse().getContentAsString());

        // Valida o código de status explicitamente
        assertThat(meResult.getResponse().getStatus()).isEqualTo(200);

        // Verificações de contrato: o corpo deve conter os campos "name" e "username" esperados
        assertThat(
                objectMapper.readTree(meResult.getResponse().getContentAsString())
                        .get("name")
                        .asText()
        ).isEqualTo("Test User");

        assertThat(
                objectMapper.readTree(meResult.getResponse().getContentAsString())
                        .get("username")
                        .asText()
        ).isEqualTo("test@example.com");
    }
}