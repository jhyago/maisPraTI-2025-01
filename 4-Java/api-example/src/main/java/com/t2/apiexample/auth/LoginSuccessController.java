package com.t2.apiexample.auth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * CONTROLADOR DE PÓS-LOGIN (OAuth2)
 *
 * Objetivo:
 *  - Depois que o login via OAuth2 (ex.: GitHub/Google) é concluído com sucesso,
 *    o Spring Security redireciona para esta rota (/loginSuccess).
 *  - Aqui pegamos o usuário autenticado (principal), extraímos atributos do
 *    provedor (ex.: "name", "login") e colocamos no Model para a view renderizar.
 *
 * Conceitos-chave:
 *  - @Controller: indica um controlador MVC que retorna NOME DE VIEW (ex.: "login")
 *    e não JSON. A resolução da view é feita pelo ViewResolver (Thymeleaf, JSP, etc.).
 *  - @AuthenticationPrincipal: injeta o usuário autenticado na requisição corrente.
 *    Com OAuth2, ele virá como OAuth2User (um "mapa" de atributos do provedor).
 *  - Model: "sacola" de atributos que serão entregues à view (HTML).
 *
 * Templates:
 *  - O retorno "login" procura um template como:
 *      src/main/resources/templates/login.html   (se usar Thymeleaf)
 *  - Dentro do template, você acessa os atributos com, por exemplo, Thymeleaf:
 *      <span th:text="${name}"></span>
 *      <span th:text="${login}"></span>
 *
 * Segurança / Observações:
 *  - Nem todos os provedores retornam os mesmos atributos:
 *      * GitHub: "login" (username), "name", "avatar_url", "html_url"
 *      * Google: geralmente "name", "email", "picture", "given_name", "family_name"
 *    → Sempre trate nulos e/ou adapte os nomes (ver dica no código).
 *  - Se a rota /loginSuccess for sensível, garanta que está protegida por Spring Security
 *    (configuração de antMatchers/HttpSecurity).
 *  - Se for exibir dados do perfil, cuide de CSRF nas ações de POST (para views com forms).
 */
@Controller
public class LoginSuccessController {

    /**
     * Endpoint acessado após sucesso no login OAuth2.
     *
     * @param principal usuário autenticado (pode ser nulo se não houver sessão)
     * @param model     objeto para enviar dados à view
     * @return nome lógico da view (template "login.html")
     */
    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model) {
        // Em cenários normais de OAuth2 login bem-sucedido, principal NÃO será nulo.
        // Ainda assim, é boa prática checar para evitar NullPointerException.
        if (principal != null) {

            // ------------------------------
            // Extraindo atributos do provider
            // ------------------------------
            // Atenção: os nomes das chaves dependem do provedor.
            // GitHub: "login" (username público), "name" (nome exibido)
            // Google: "name" (nome completo), "email", "picture"
            //
            // getAttribute retorna Object; convertemos para String com null-safety.
            String displayName = safeString(principal.getAttribute("name"));
            String usernameLike = safeString(principal.getAttribute("login")); // GitHub usa "login"
            // Para Google, poderia usar:
            // String email = safeString(principal.getAttribute("email"));
            // String picture = safeString(principal.getAttribute("picture"));

            // ------------------------------
            // Enviando dados para a view
            // ------------------------------
            model.addAttribute("name", displayName);
            model.addAttribute("login", usernameLike);

            // Dica: envie também o "provider" ou todo o mapa de atributos se a view
            // precisar decidir o que mostrar conforme o provedor:
            // model.addAttribute("attributes", principal.getAttributes());
            // model.addAttribute("provider", principal.getAttribute("iss")); // depende do provider
        }

        // Retornamos o nome lógico da view — ex.: templates/login.html (Thymeleaf)
        return "login";
    }

    /**
     * Helper simples para converter atributos possivelmente nulos em String segura.
     * Evita NPEs e facilita exibir "desconhecido" na view quando o provedor não envia algo.
     */
    private String safeString(Object value) {
        return value == null ? "desconhecido" : String.valueOf(value);
    }
}