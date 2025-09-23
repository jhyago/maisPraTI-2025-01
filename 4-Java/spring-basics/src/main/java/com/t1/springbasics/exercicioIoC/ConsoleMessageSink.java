package com.t1.springbasics.exercicioIoC;

public class ConsoleMessageSink implements MessageSink {
    @Override
    public void show(String message) {
        System.out.println(message);
    }
}
