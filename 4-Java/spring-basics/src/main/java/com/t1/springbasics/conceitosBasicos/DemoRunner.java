package com.t1.springbasics.conceitosBasicos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//Crie um mini-programa que some números sem “dar new” dentro da lógica principal. Imagine um “contador” que precisa guardar um total e escrever mensagens na tela. Primeiro, descreva duas peças de que a lógica precisa: algo que saiba ler e gravar o total, e algo que saiba exibir mensagens. Em seguida, escreva a regra: “somar um valor ao total e anunciar o novo total”. Essa regra não deve conhecer classes concretas; ela apenas recebe, no construtor, quem lê/grava e quem exibe mensagens. No ponto de entrada do programa, fora da regra, você escolhe quais implementações usar, cria os objetos concretos e os entrega prontos para a regra. Rode somando dois valores e observe as mensagens.

//Para provar a inversão de controle, troque somente no ponto de entrada a implementação que guarda o total por outra equivalente e execute de novo.

@Component
public class DemoRunner implements CommandLineRunner {
    private final Carrinho carrinho;

    DemoRunner(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("-> Carrinho: " + carrinho.andar());
    }
}
