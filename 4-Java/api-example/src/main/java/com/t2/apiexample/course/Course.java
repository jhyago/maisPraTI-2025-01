package com.t2.apiexample.course;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

/**
 * ENTIDADE JPA: Course
 *
 * Papel:
 *  - Representa a tabela "courses" no banco de dados.
 *  - Usa Bean Validation para validar entrada (ex.: no controller com @Valid).
 *  - Mantém timestamps (createdAt/updatedAt) via callbacks JPA (@PrePersist/@PreUpdate).
 *
 * Observações importantes:
 *  - Validações (@NotBlank, @Size) NÃO impedem valores inválidos no banco por si só;
 *    elas atuam quando você chama a validação (ex.: @Valid no controller/service).
 *  - Para reforçar no banco, combine com constraints/DDL (ex.: NOT NULL, comprimento da coluna).
 *  - Instant armazena tempo em UTC — ótimo para consistência entre fusos.
 */
@Entity
@Table(
        name = "courses",
        uniqueConstraints = {
                // Garante unicidade do NOME no banco (além de validar na aplicação).
                // Útil para evitar duplicidades concorrentes.
                @UniqueConstraint(columnNames = "name")
        }
)
public class Course {

    // Chave primária; gerada pelo banco (IDENTITY = auto-increment na maioria dos bancos).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do curso: obrigatório (não aceita nulo nem string em branco) e limitado a 150 chars.
    // Dica: combine com @Column(nullable = false, length = 150) para reforçar no banco.
    @NotBlank
    @Size(max = 150)
    private String name;

    // Descrição opcional (pode ser nula), limitada a 1000 chars.
    // Dica: se planejar textos muito longos, considere @Lob ou TEXT no DDL.
    @Size(max = 1000)
    private String description;

    // Timestamps de auditoria simples.
    // Instant = UTC; evita problemas de fuso horário em produção.
    private Instant createdAt;
    private Instant updatedAt;

    /**
     * CALLBACK DE CICLO DE VIDA (pré-inserção)
     *
     * Executa antes do INSERT:
     *  - Define createdAt e updatedAt com o "agora" (Instant.now()).
     *  - Garante que ambos os campos não fiquem nulos na criação.
     *
     * Alternativas:
     *  - Hibernate: @CreationTimestamp/@UpdateTimestamp (automatiza sem escrever callbacks)
     *  - Spring Data JPA: Auditoria com @CreatedDate/@LastModifiedDate + @EnableJpaAuditing
     */
    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * CALLBACK DE CICLO DE VIDA (pré-atualização)
     *
     * Executa antes do UPDATE:
     *  - Atualiza updatedAt para o momento atual.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    // ---------------------------
    // GETTERS e SETTERS (POJO)
    // ---------------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id; // normalmente não setamos id manualmente quando é IDENTITY
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // Boa prática: normalizar entrada (ex.: trim) para reduzir chances de duplicidade "acidental".
        // this.name = name == null ? null : name.trim();
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        // this.description = description == null ? null : description.trim();
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    // Em geral, não expomos setCreatedAt publicamente (apenas callbacks/auditoria ajustam).
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    // Idem: normalmente updatedAt é controlado pelos callbacks/auditoria.
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}