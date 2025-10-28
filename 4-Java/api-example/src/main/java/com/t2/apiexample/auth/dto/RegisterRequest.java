package com.t2.apiexample.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * RegisterRequest
 *
 * DTO (Data Transfer Object) usado para RECEBER os dados do cadastro
 * de um novo usuário pela API (provavelmente em um POST /auth/register).
 *
 * Por que DTO?
 * - Separa o "contrato" de entrada (campos e validações) da sua entidade de domínio.
 * - Evita vazar detalhes internos do modelo (ex.: IDs, flags).
 *
 * Validações via Bean Validation (Jakarta Validation):
 * - @NotBlank: rejeita null, "", e strings só com espaços.
 * - @Size: impõe limites de tamanho (mínimo/máximo).
 * - @Email: valida formato de e-mail (sintaxe RFC básica; não garante que o domínio exista).
 *
 * Observação importante:
 * - A senha recebida aqui deve ser tratada com CUIDADO:
 *   * Nunca logar o valor.
 *   * Hashear (ex.: BCrypt) ANTES de persistir.
 *   * Avaliar políticas (complexidade mínima, breach check, etc.).
 */
public class RegisterRequest {

    // -----------------------------
    // NOME COMPLETO DO USUÁRIO
    // -----------------------------
    @NotBlank                  // não aceita nulo ou string em branco
    @Size(min = 2, max = 120)  // nomes muito curtos/longos são rejeitados
    private String name;

    // Getter: expõe o valor para o controller/serviço
    public String getName() {
        return name;
    }

    // Setter: recebe o valor do JSON → DTO
    // Dica: é comum normalizar aqui (ex.: trim) para evitar erros por espaços.
    public void setName(String name) {
        // Normalização opcional (descomente se quiser aplicar)
        // this.name = name == null ? null : name.trim();
        this.name = name;
    }

    // -----------------------------
    // USUÁRIO / LOGIN (E-MAIL)
    // -----------------------------
    @NotBlank
    @Size(max = 120)  // limite por segurança/compatibilidade com colunas de banco
    @Email            // valida formato de e-mail; não checa MX/entrega
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        // Ex.: normalizar e-mail para lower-case e trim (boa prática)
        // this.username = username == null ? null : username.trim().toLowerCase(Locale.ROOT);
        this.username = username;
    }

    // -----------------------------
    // SENHA
    // -----------------------------
    @NotBlank
    @Size(min = 8, max = 72)
    /*
     * Por que max = 72?
     * - BCrypt tradicional considera apenas os 72 primeiros bytes.
     *   Manter esse teto evita frustração do usuário (entrada maior sendo truncada).
     * - Se usar Argon2/scrypt/PBKDF2, o teto pode ser diferente. Ajuste conforme sua estratégia.
     */
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // NUNCA faça logs com a senha aqui
        // Ex.: possível política: bloquear espaços extremos, validar força, etc.
        this.password = password;
    }
}