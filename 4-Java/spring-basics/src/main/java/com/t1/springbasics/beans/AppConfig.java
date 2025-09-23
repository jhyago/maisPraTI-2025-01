package com.t1.springbasics.beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configuration
public class AppConfig {
    @Bean
    public String prefixo() {
        return ">>>";
    }
}

@Service
class Greeter() {
    private final String prefixo;
    Greeter(String prefixo) {
        this.prefixo = prefixo;
    }

    String Say(String nome) { return prefixo + nome; }
}

@Component
Class MusicBox {
    @PostConstruct void init() {
        System.out.println("Iniciando MusicBox");
    }

    @PreDestroy void close () {
        System.out.println("Finalizando MusicBox");
    }
}
