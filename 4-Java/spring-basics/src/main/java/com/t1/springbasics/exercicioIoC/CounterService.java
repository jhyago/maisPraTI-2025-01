package com.t1.springbasics.exercicioIoC;

import java.math.BigDecimal;

public class CounterService {
    private final TotalStore store;
    private final MessageSink sink;

    public CounterService(TotalStore store, MessageSink sink) {
        this.store = store;
        this.sink = sink;
    }

    public void add(BigDecimal value) {
        BigDecimal current = store.read();
        BigDecimal update = current.add(value);

        store.write(update);
        sink.show("Novo total: " + update);
    }
}
