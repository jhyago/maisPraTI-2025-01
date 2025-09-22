package com.t1.springbasics.conceitosBasicos;

import org.springframework.stereotype.Service;

@Service
public class MotorEletrico implements Motor {
    public String ligar() {return "Motor ligado!";}
}
