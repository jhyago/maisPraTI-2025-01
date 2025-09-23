package com.t1.springbasics.exercicioIoC;

import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

@Profile("!file")
public class InMemoryTotalStore implements TotalStore {
    private final AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);
    @Override
    public BigDecimal read() {
        return total.get();
    }

    @Override
    public void write(BigDecimal newTotal) {
        total.set(newTotal);
    }
}
