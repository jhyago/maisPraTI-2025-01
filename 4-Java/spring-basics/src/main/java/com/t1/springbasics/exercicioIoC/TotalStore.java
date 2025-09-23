package com.t1.springbasics.exercicioIoC;

import java.math.BigDecimal;

public interface TotalStore {
    BigDecimal read();

    void write(BigDecimal newTotal);
}
