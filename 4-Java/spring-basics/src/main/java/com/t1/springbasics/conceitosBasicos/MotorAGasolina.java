package com.t1.springbasics.conceitosBasicos;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
@Primary
@Service
public class MotorAGasolina implements Motor {
    public String ligar() {return "Motor a gasolina ligado!";}
}
