package com.t1.springbasics.exercicioIoC;

import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

@Profile("file")
public class FileTotalStore implements TotalStore {
    private final Path file;

    public FileTotalStore(Path file) {
        this.file = file;
    }

    @Override
    public BigDecimal read() {
        try {
            if(Files.notExists(file)) return BigDecimal.ZERO;
            String s = Files.readString(file).trim();
            return s.isEmpty() ? BigDecimal.ZERO : new BigDecimal(s);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao ler o total do arquivo.");
        }
    }

    @Override
    public void write(BigDecimal newTotal) {
        try {
            Files.createDirectories(file.getParent());
            Files.writeString(file, newTotal.toPlainString());
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar o total do arquivo.");
        }
    }
}
