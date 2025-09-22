package com.t1.springbasics.conceitosBasicos;

import org.springframework.stereotype.Service;

@Service
public class Carrinho {
    private final Motor motor;

    public Carrinho(Motor motor) {
        this.motor = motor;
    }

    String andar() { return motor.ligar() + " E o carinho está andando"; }
}
