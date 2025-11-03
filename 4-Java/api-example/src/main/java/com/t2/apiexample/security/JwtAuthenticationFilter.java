package com.t2.apiexample.security;

import com.t2.apiexample.user.User;
import com.t2.apiexample.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * FILTRO DE AUTENTICAÇÃO POR JWT
 *
 * Responsabilidade:
 *  - Rodar em TODA requisição (OncePerRequestFilter garante 1x por request).
 *  - Ler o header Authorization: "Bearer <token>".
 *  - Validar token (assinatura/expiração).
 *  - Carregar o usuário/roles e popular o SecurityContext com uma Authentication.
 *
 * Observações:
 *  - O filtro NÃO envia 401 automaticamente neste exemplo; se o token for inválido,
 *    a cadeia segue "anônima" e a camada de autorização pode barrar a rota protegida.
 *  - A ordem de execução é configurada na SecurityConfig: .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
 *    → isso garante que, se o token for válido, já há Authentication antes dos demais filtros.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    /**
     * Núcleo do filtro:
     * - Tenta extrair e validar o JWT.
     * - Se OK, cria Authentication e coloca no SecurityContext.
     * - Em qualquer caso, chama o próximo elemento da cadeia (filterChain.doFilter).
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1) Pega o header padrão de Authorization
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 2) Checa se está no formato "Bearer <token>"
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extrai somente o token (sem o prefixo)
            String token = authHeader.substring(7);

            // 3) Valida criptograficamente + expiração.
            //    - validateToken encapsula parse + verificação de assinatura/exp (true/false).
            if (jwtService.validateToken(token)) {

                // 4) Extrai o "subject" do token (geralmente username/email)
                String username = jwtService.getSubject(token);

                // Dica de robustez: se já existe Authentication no contexto (ex.: chain anterior),
                // você poderia pular o reprocessamento:
                // if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { ... }

                // 5) Carrega o usuário do repositório (fonte de verdade das roles/flags)
                User user = userRepository.findByUsername(username).orElse(null);

                if (user != null) {
                    // 6) Converte as roles do domínio para GrantedAuthority do Spring.
                    //    - Por convenção, roles são authorities com prefixo "ROLE_"
                    var authorities = user.getRoles().stream()
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                            .collect(Collectors.toSet());

                    // 7) Cria um Authentication já autenticado (credenciais = null pois usamos JWT)
                    //    - principal: aqui usamos o próprio username (String).
                    //      Alternativa: usar um UserDetails para expor flags (isEnabled, etc.)
                    var authentication = new UsernamePasswordAuthenticationToken(
                            username,              // principal (quem é o usuário)
                            null,                  // credentials (não precisamos da senha aqui)
                            authorities            // authorities (ROLE_*)
                    );

                    // 8) Coloca a Authentication no contexto de segurança da thread atual.
                    //    - A partir daqui, @PreAuthorize, hasRole, etc. já "enxergam" o usuário.
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                // Caso user seja null: token válido, mas usuário não encontrado → segue anônimo.
                // Dependendo da sua política, você poderia limpar o contexto e/ou devolver 401 aqui.
            }
            // Se o token for inválido/expirado, simplesmente não autentica.
            // A autorização de rotas protegidas resultará em 401/403 mais adiante.
        }

        // 9) Continua a cadeia de filtros (sempre chame)
        filterChain.doFilter(request, response);
    }
}