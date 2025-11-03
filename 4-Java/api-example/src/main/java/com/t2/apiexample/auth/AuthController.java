package com.t2.apiexample.auth;

import com.t2.apiexample.auth.dto.AuthResponse;
import com.t2.apiexample.auth.dto.LoginRequest;
import com.t2.apiexample.auth.dto.RegisterRequest;
import com.t2.apiexample.security.JwtService;
import com.t2.apiexample.user.User;
import com.t2.apiexample.user.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; // (não usado aqui; poderia ser removido ou usado p/ autenticar)
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * CONTROLADOR DE AUTENTICAÇÃO
 *
 * Endpoints:
 *  - POST /auth/register : cria um novo usuário e já devolve um JWT
 *  - POST /auth/login    : valida credenciais e devolve um JWT
 *
 * Padrões adotados:
 *  - Recebimento de DTOs validados (@Valid) -> garante formato antes da lógica.
 *  - Status HTTP apropriados:
 *      * 201 Created (registro OK), 409 Conflict (username em uso)
 *      * 200 OK (login OK), 401 Unauthorized (credenciais inválidas)
 *  - Senhas NUNCA são retornadas e SEMPRE são armazenadas com hash (PasswordEncoder).
 *  - JWT contém "claims" úteis (name, roles) — cuidado com dados sensíveis!
 *
 * Swagger/OpenAPI:
 *  - @Tag adiciona o grupo "Authentication" na documentação.
 */
@RestController
@Tag(name = "Authentication")
@RequestMapping("/auth")
public class AuthController {
    // Repositório para persistir/consultar usuários (provavelmente JPA)
    private final UserRepository userRepository;

    // Responsável por aplicar hash seguro na senha (ex.: BCrypt)
    private final PasswordEncoder passwordEncoder;

    // AuthenticationManager poderia ser usado para fluxo de auth do Spring Security;
    // neste código, a validação está manual (matches). Considerar uso futuro para consistência.
    private final AuthenticationManager authenticationManager;

    // Serviço que gera tokens JWT assinado (deve incluir secret/expiração)
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * REGISTRO DE USUÁRIO
     *
     * Fluxo:
     *  1) Valida DTO (@Valid) — Bean Validation vai bloquear requisições inválidas.
     *  2) Checa se o username já existe (409 Conflict em caso positivo).
     *  3) Cria entidade User:
     *      - normaliza username para lowercase (evita duplicidade case-insensitive)
     *      - aplica hash na senha (NUNCA salve senha em claro)
     *  4) Salva o usuário.
     *  5) Gera JWT com claims mínimas (name, roles) — avalie LGPD/privacidade.
     *  6) Retorna 201 Created com corpo { token: "<jwt>" }.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        // Verificação de unicidade do "username" (e-mail). Boa prática: normalizar antes de consultar.
        if (userRepository.existsByUsername(request.getUsername())) {
            // 409 sinaliza conflito de recurso (username já usado)
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Username já existe!"));
        }

        // Mapeia DTO -> Entidade. Mantenha a transformação simples e centralizada.
        User user = new User();
        user.setName(request.getName());

        // Normaliza e-mail para lowercase; ideal também aplicar trim no DTO/setter
        user.setUsername(request.getUsername().toLowerCase());

        // Hash seguro da senha (ex.: BCrypt). Nunca retorne essa senha depois.
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Persiste no banco
        userRepository.save(user);

        // Claims que irão dentro do JWT. Evitar colocar dados sensíveis/P.I.I. aqui.
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        claims.put("roles", user.getRoles()); // garanta que roles seja uma coleção simples (ex.: ["ROLE_USER"])

        // Gera o token com subject = username e claims adicionais
        String token = jwtService.generateToken(user.getUsername(), claims);

        // 201 Created: recurso criado com sucesso (neste caso, o usuário).
        // Retornamos apenas o token como resposta (sem dados sensíveis).
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token));
    }

    /**
     * LOGIN
     *
     * Fluxo:
     *  1) Valida DTO (@Valid)
     *  2) Busca usuário por username normalizado (lowercase)
     *  3) Compara senha digitada (raw) com hash salvo (passwordEncoder.matches)
     *  4) Se falhar, 401 Unauthorized (sem corpo) — por segurança, não detalhe o erro.
     *  5) Se OK, gera JWT e retorna 200 OK { token: "<jwt>" }
     *
     * Observações:
     *  - Considerar proteção contra brute force (limite de tentativas, captcha, backoff).
     *  - Avaliar retorno de um "refresh token" em fluxos mais complexos.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        // Normalizamos para lowercase para manter coerência na busca
        var username = request.getUsername().toLowerCase();

        // Busca o usuário
        var userOpt = userRepository.findByUsername(username);

        // Caso não exista ou a senha não bata, 401 (não revelar qual das duas falhou)
        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Usuário autenticado
        var user = userOpt.get();

        // Monta claims (metadados do token). Evite payloads grandes e sensíveis.
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        claims.put("roles", user.getRoles());

        // Gera token JWT com subject = username
        String token = jwtService.generateToken(username, claims);

        // 200 OK com o token no corpo
        return ResponseEntity.ok(new AuthResponse(token));
    }
}