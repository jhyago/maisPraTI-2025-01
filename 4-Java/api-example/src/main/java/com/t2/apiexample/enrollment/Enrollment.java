package com.t2.apiexample.enrollment;

import com.t2.apiexample.course.Course;
import com.t2.apiexample.user.User;
import jakarta.persistence.*;

import java.time.Instant;

/**
 * ENTIDADE JPA: Enrollment (matrícula)
 *
 * Papel:
 *  - Representa a MATRÍCULA de um usuário em um curso.
 *  - É uma relação N:1 com User e N:1 com Course (ou seja, "muitas matrículas" para um usuário,
 *    e "muitas matrículas" para um curso).
 *  - A combinação (user_id, course_id) deve ser ÚNICA (um mesmo usuário não se matricula duas vezes no mesmo curso).
 */
@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = {
                // Garante no BANCO que a mesma dupla (user, course) não se repita.
                // Isso protege contra condições de corrida / requests simultâneos.
                @UniqueConstraint(columnNames = {"user_id", "course_id"}),
        }
)
public class Enrollment {

    // Chave primária auto-increment (gerada pelo banco).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELAÇÃO: muitas matrículas → para UM usuário
    // - @ManyToOne: lado N de um relacionamento N:1
    // - @JoinColumn: nome da coluna FK nesta tabela "enrollments"
    // Dicas:
    //  * fetch = LAZY (padrão do ManyToOne no JPA 2.0+ em alguns providers) ajuda performance
    //  * cascade NUNCA para REMOVE aqui (evite deletar user ao remover matrícula!)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // RELAÇÃO: muitas matrículas → para UM curso
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // Momento (UTC) em que a matrícula foi criada.
    private Instant enrolledAt;

    /**
     * CALLBACK DE CICLO DE VIDA – executa antes do INSERT.
     * Define automaticamente o timestamp de criação (UTC).
     */
    @PrePersist
    public void prePersist() {
        this.enrolledAt = Instant.now();
    }

    // ---------------------------
    // GETTERS e SETTERS
    // ---------------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        // Em geral, o ID é gerado pelo banco; setters de ID raramente são usados.
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        // Boa prática: validar não-nulo em nível de serviço antes de persistir.
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        // Idem: validar não-nulo e existência do curso no serviço.
        this.course = course;
    }

    public Instant getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(Instant enrolledAt) {
        // Normalmente controlado pelos callbacks/auditoria (evite expor publicamente).
        this.enrolledAt = enrolledAt;
    }

    // ------------------------------------------------------------------
    // DICAS IMPORTANTES (opcionais para implementar agora ou futuramente)
    // ------------------------------------------------------------------
    // 1) @Column(nullable = false) nas FKs e no enrolledAt reforça a integridade no banco:
    //    @ManyToOne @JoinColumn(name="user_id", nullable=false)
    //    @ManyToOne @JoinColumn(name="course_id", nullable=false)
    //    @Column(nullable=false) private Instant enrolledAt;
    //
    // 2) Índices: além da UNIQUE(user_id, course_id), crie índices individuais
    //    para acelerar consultas por user ou por course, se forem comuns:
    //    @Table(indexes = {
    //        @Index(name="idx_enrollments_user", columnList="user_id"),
    //        @Index(name="idx_enrollments_course", columnList="course_id")
    //    })
    //
    // 3) equals() / hashCode(): se você usar a entidade em Sets/Maps, implemente com base
    //    em uma chave imutável. Para entidades JPA, costuma-se usar o ID após persistido
    //    (ou uma chave natural como (user, course) com cuidado):
    //    - Evite usar coleções p/ equals/hashCode (pode causar ciclos).
    //
    // 4) FetchType e Cascade:
    //    - ManyToOne normalmente é LAZY em providers atuais; explicitar pode ser útil:
    //      @ManyToOne(fetch = FetchType.LAZY)
    //    - NÃO use cascade REMOVE aqui (não queremos apagar User/Course ao deletar uma matrícula).
    //
    // 5) Camada de serviço:
    //    - Antes de salvar, valide se (user, course) já existe (consulta + tratar 409/422).
    //    - Ainda assim, confie na UNIQUE do banco para garantir integridade sob concorrência.
    //
    // 6) Auditoria avançada:
    //    - Se quiser updatedAt ou auditoria de quem realizou a matrícula:
    //      use Spring Data JPA Auditing (@CreatedDate, @CreatedBy) com @EnableJpaAuditing.
}