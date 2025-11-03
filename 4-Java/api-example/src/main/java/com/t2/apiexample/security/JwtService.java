package com.t2.apiexample.security;

import io.jsonwebtoken.*;                       // Tipos principais do JJWT (Jws, Claims, JwtException, etc.)
import io.jsonwebtoken.security.Keys;           // Helper para criar chaves HMAC/Assinatura
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;        // Para garantir codificação estável (UTF-8)
import java.security.Key;                       // Representa a chave HMAC
import java.time.Instant;                       // Tempo em UTC
import java.util.Date;                          // API de data usada pelo JJWT
import java.util.Map;

/**
 * JwtService
 *
 * Responsabilidade:
 *  - Gerar, analisar (parse) e validar tokens JWT HS256.
 *  - Centraliza a chave, algoritmo e política de expiração.
 *
 * Conceitos essenciais:
 *  - JWT = header.payload.signature
 *    * header: metadados (algoritmo, tipo)
 *    * payload: claims (sub, iat, exp, dados adicionais)
 *    * signature: proteção de integridade/autenticidade (HMAC com secret, neste caso)
 *
 * Segurança:
 *  - HS256 (HMAC-SHA256) exige uma chave SECRETA forte (>= 256 bits ≈ 32 bytes).
 *  - Nunca compartilhe o secret; mantenha-o em variáveis de ambiente/secret manager.
 *  - Tokens devem expirar (curtos). Para sessões longas, use refresh tokens.
 */
@Service
public class JwtService {

    private final Key key;                 // Chave HMAC usada para assinar/verificar tokens
    private final Long expirationMillis;   // Validade do token (em milissegundos)

    /**
     * @param secret            chave HMAC em texto (deve ter >= 32 caracteres para HS256)
     * @param expirationMillis  validade do token em milissegundos (padrão: 86.400.000 = 24h)
     *
     * Boas práticas:
     *  - injete via configuração/variável de ambiente (NÃO versionar em repositório!)
     *  - valide o tamanho da chave para evitar “weak key” (JJWT também valida internamente)
     */
    public JwtService(
            @Value("${security.jwt.secret:12345678901234567890123456789012}") String secret,
            @Value("${security.jwt.expiration:86400000}") Long expirationMillis
    ) {
        if (secret == null || secret.trim().length() < 32) {
            // Mensagem clara e corrigida (“tem que ter”)
            throw new IllegalArgumentException("JWT secret tem que ter pelo menos 32 caracteres (HS256).");
        }

        // Constrói a Key HMAC a partir do secret textual
        // - Use UTF-8 explicitamente para bytes consistentes
        this.key = Keys.hmacShaKeyFor(secret.trim().getBytes(StandardCharsets.UTF_8));
        this.expirationMillis = expirationMillis;
    }

    /**
     * GERA UM TOKEN JWT
     *
     * @param subject  “quem” é o dono do token (geralmente username/ID)
     * @param claims   mapa de claims adicionais (ex.: name, roles). Evite dados sensíveis!
     * @return token compactado (String "header.payload.signature")
     *
     * Campos padrão:
     *  - sub: subject
     *  - iat: emitido em (Issued At)
     *  - exp: expira em (Expiration)
     *
     * Observações:
     *  - claims grandes aumentam tamanho do token (custa em rede e headers).
     *  - use roles/authorities simples (strings). Evite objetos pesados.
     */
    public String generateToken(String subject, Map<String, Object> claims) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(subject)                                   // sub
                .addClaims(claims)                                     // payload adicional
                .setIssuedAt(Date.from(now))                           // iat
                .setExpiration(Date.from(now.plusMillis(expirationMillis))) // exp
                .signWith(key, SignatureAlgorithm.HS256)               // assinatura HMAC-SHA256
                .compact();
    }

    /**
     * PARSE/VERIFICAÇÃO DO TOKEN
     *
     * @param token JWT no formato compact (sem o prefixo "Bearer ")
     * @return Jws<Claims> já verificado (assinatura/expiração). Se inválido/expirado, lança exceção.
     */
    public Jws<Claims> parseToken(String token) {
        return Jwts
                .parserBuilder()        
                .setSigningKey(key)     // chave para verificar a assinatura
                .build()
                .parseClaimsJws(token); // lança JwtException se inválido/expirado
    }

    /**
     * Extrai o subject (sub) de um token.
     */
    public String getSubject(String token) {
        return parseToken(token).getBody().getSubject();
    }

    /**
     * Valida o token de forma “booleana”.
     *
     * @return true se parsing/assinatura/expiração OK; false se inválido/expirado/malformado.
     *
     * Tratamento:
     *  - JwtException cobre ExpiredJwtException, SignatureException, MalformedJwtException, etc.
     *  - IllegalArgumentException cobre token nulo/vazio.
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Em produção, você pode logar a razão (sem expor detalhes ao cliente).
            return false;
        }
    }
}