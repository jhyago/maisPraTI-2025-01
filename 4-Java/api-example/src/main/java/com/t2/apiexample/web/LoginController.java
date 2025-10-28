package com.t2.apiexample.web; 
import org.springframework.security.core.annotation.AuthenticationPrincipal; // Importa a anotação que injeta o usuário autenticado no método.
import org.springframework.security.oauth2.core.user.OAuth2User;            // Interface que representa o usuário autenticado via OAuth2.
import org.springframework.stereotype.Controller;                         // Anotação usada para marcar a classe como um controlador MVC 
import org.springframework.ui.Model;                                      // Interface usada para passar dados para a view (templates).
import org.springframework.web.bind.annotation.GetMapping;                // Anotação para mapear requisições HTTP GET aos métodos.
@Controller                                                                 // Indica ao Spring que esta classe contém handlers de requisição (controlador web).
public class LoginController {                                              // Declara a classe do controlador.

    @GetMapping("/")                                                        // Mapeia requisições GET para a URL raiz "/" a este método.
    public String home() {                                                  // Método handler que lida com a rota raiz.
        return "index";                                                     // Retorna o nome da view "index" (ex.: index.html) para ser renderizada.
    }

    @GetMapping("/login")                                                   // Mapeia requisições GET para a URL "/login" a este método.
    public String login(Model model,                                        // Recebe o Model para enviar dados à view.
                        @AuthenticationPrincipal OAuth2User principal) {    // Injeta o usuário autenticado (via Spring Security OAuth2) como parâmetro.
        model.addAttribute("name",                                          // Adiciona ao modelo um atributo chamado "name"
                           principal.getAttribute("name"));                 // com o valor do atributo "name" do usuário OAuth2 (ex.: nome completo).
        model.addAttribute("login",                                         // Adiciona outro atributo chamado "login"
                           principal.getAttribute("login"));                // com o valor do atributo "login" (ex.: username do GitHub).
        return "login";                                                     // Retorna o nome da view "login" para renderização.
    }                                                                                                                                                   
}