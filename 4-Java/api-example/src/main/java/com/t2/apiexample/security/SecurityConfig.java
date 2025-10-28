package com.t2.apiexample.security;

import com.t2.apiexample.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity // Habilita segurança ANOTADA em métodos (ex.: @PreAuthorize("hasRole('ADMIN')"))
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    // Filtro customizado que:
    // - Lê o header Authorization: Bearer <jwt>
    // - Valida o token 
    // - Carrega o usuário e popula o SecurityContext com Authentication
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Repositório de usuários; usado pelo UserDetailsService
    private final UserRepository userRepository;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, UserRepository userRepository) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userRepository = userRepository;
    }

    // -----------------------------------------------------------------------------
    // PasswordEncoder: NUNCA guarde senhas em texto puro. Use hash seguro (ex.: BCrypt).
    // -----------------------------------------------------------------------------
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // -----------------------------------------------------------------------------
    // UserDetailsService: ponte entre seu modelo de usuário e o Spring Security.
    // - Recebe "username" (login)
    // - Retorna um UserDetails (credenciais + authorities) que o Spring entende
    // -----------------------------------------------------------------------------
    @Bean
    public UserDetailsService userDetailsService () {
        return username -> userRepository.findByUsername(username)
                .map(u -> (UserDetails) org.springframework.security.core.userdetails.User
                        .withUsername(u.getUsername())
                        .password(u.getPassword()) // senha já codificada (hash)
                        // IMPORTANTE: roles x authorities
                        // - .roles("USER","ADMIN") adiciona automaticamente o prefixo "ROLE_"
                        // - Se "u.getRoles()" já vier sem prefixo, esta chamada é adequada.
                        // - Se você controla authorities finas, use .authorities(...)
                        .roles(u.getRoles().toArray(String[]::new))
                        // Campos próprios do Spring Security (controles de conta):
                        .accountLocked(false) // true = bloqueia login (ex.: tentativas excessivas)
                        .disabled(false)      // true = usuário desativado (sem acesso)
                        // Outras opções disponíveis: accountExpired, credentialsExpired
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    // -----------------------------------------------------------------------------
    // DaoAuthenticationProvider: provedor de autenticação que usa UserDetailsService + PasswordEncoder.
    // - Útil quando você usa login tradicional (form) ou quer que o AuthenticationManager valide as credenciais.
    // -----------------------------------------------------------------------------
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // de onde vem o usuário
        authProvider.setPasswordEncoder(passwordEncoder);       // como comparar senha raw com hash
        return authProvider;
    }

    // -----------------------------------------------------------------------------
    // AuthenticationManager: orquestra os AuthenticationProviders.
    // - Útil para autenticar credenciais no seu fluxo (ex.: login manual).
    // -----------------------------------------------------------------------------
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // usa os providers registrados
    }

    // -----------------------------------------------------------------------------
    // CORS: permite que front-ends em outras origens (domínios/portas) chamem sua API com cookies/headers.
    // - allowedOriginPatterns com curingas; para ambientes reais, prefira domínios explícitos.
    // - allowedHeaders/Methods/AllowCredentials: ajuste conforme necessidade do seu front.
    // -----------------------------------------------------------------------------
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // DICA: "allowedOrigins" NÃO aceita curingas com credenciais; "allowedOriginPatterns" aceita.
        config.setAllowedOriginPatterns(List.of(
                "http://localhost:5173/**"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // se vai enviar cookies/autorização, precisa estar true e origem específica

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // aplica CORS globalmente
        return source;
    }

    // -----------------------------------------------------------------------------
    // SecurityFilterChain: coração da configuração HTTP.
    // - Define CORS/CSRF, rotas públicas/privadas, login OAuth2, política de sessão e ORDEM dos filtros.
    // -----------------------------------------------------------------------------
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Habilita CORS conforme o bean acima
                .cors(Customizer.withDefaults())

                // AUTORIZAÇÃO por padrão das requisições HTTP
                .authorizeHttpRequests(auth -> auth
                        // Rotas públicas (sem autenticar)
                        .requestMatchers(
                                "/", "/index.html", "/oauth2/**", "/login/**",
                                "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**",
                                "/auth/**",
                                "/actuator/health"
                        ).permitAll()
                        // Qualquer outra rota exige usuário autenticado
                        .anyRequest().authenticated()
                )

                // LOGIN VIA OAUTH2 (ex.: GitHub/Google) → ao logar, redireciona para /loginSuccess
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("/loginSuccess", true)
                )

                // CSRF:
                // - Para APIs REST "stateless" com JWT, geralmente desabilitamos CSRF
                // - Para aplicações com formulários (cookies/sessão), o ideal é manter habilitado
                .csrf(csrf -> csrf.disable())

                // POLÍTICA DE SESSÃO:
                // - IF_REQUIRED: cria sessão se necessário (compatível com oauth2Login)
                // - STATELESS: ideal para APIs 100% JWT sem sessão (mas então ajuste oauth2)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                // Provider usado pelo AuthenticationManager (login baseado em UserDetails)
                .authenticationProvider(authenticationProvider(userDetailsService(), passwordEncoder()))

                // ORDEM DOS FILTROS:
                // - Nosso filtro de JWT roda ANTES do UsernamePasswordAuthenticationFilter
                //   para que, se houver Bearer token válido, o usuário já esteja autenticado no contexto.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}