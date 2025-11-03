package com.t2.apiexample.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * REPOSITÓRIO JPA DE MATRÍCULAS
 *
 * Pontos-chave:
 * - Métodos "derivados" (query methods) usam o NOME para gerar a consulta.
 *   Ex.: findByUserId → WHERE user.id = :userId
 * - Para propriedades aninhadas, o Spring Data permite "traversal" (User.id → UserId).
 * - @Query(nativeQuery = true) executa SQL nativo; útil para tunar performance ou recursos específicos do banco.
 * - Prefira tipos primitivos/encapsulados coerentes com o uso (long vs Long) e consistência entre camadas.
 */
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    /**
     * EXISTE MATRÍCULA PARA ESTE CURSO E ESTE USUÁRIO?
     *
     * ⚠️ IMPORTANTE: a ordem dos parâmetros deve seguir a ordem dos campos no nome do método.
     *   existsByCourseIdAndUserId(COURSE_ID, USER_ID)
     * Se você inverter (UserId, CourseId), o Spring atribui valores trocados e a consulta fica incorreta.
     */
    boolean existsByCourseIdAndUserId(Long courseId, Long userId);

    /**
     * LISTA MATRÍCULAS DE UM USUÁRIO (por id)
     * Derivada: WHERE user.id = :userId
     */
    List<Enrollment> findByUserId(Long userId);

    /**
     * LISTA MATRÍCULAS DE UM CURSO (por id)
     * Derivada: WHERE course.id = :courseId
     */
    List<Enrollment> findByCourseId(Long courseId);

    /**
     * IDs de usuários matriculados em um curso (SQL nativo), ordenados por data de matrícula ascendente.
     *
     * Por que nativo?
     * - Às vezes é útil retornar somente os IDs (evita carregar entidades),
     *   com ordenação específica e potencialmente usar índices/otimizações do banco.
     *
     * Observações:
     * - @Param evita injeção de SQL e deixa a consulta mais legível.
     * - Garanta índices em (course_id) e na UNIQUE (user_id, course_id) para performance.
     */
    @Query(
        value = """
                SELECT e.user_id
                FROM enrollments e
                WHERE e.course_id = :courseId
                ORDER BY e.enrolled_at ASC
                """,
        nativeQuery = true
    )
    List<Long> findUserIdsByCourseNative(@Param("courseId") Long courseId);

    /**
     * Contagem de matrículas em um curso (SQL nativo).
     *
     * Dica: para alinhar com o Java, usar 'long' aqui é ótimo; na camada de serviço/controller
     * você pode receber como 'long' ou 'Long' (autoboxing cuida).
     */
    @Query(
        value = """
                SELECT COUNT(*)
                FROM enrollments e
                WHERE e.course_id = :courseId
                """,
        nativeQuery = true
    )
    long countByCourseIdNative(@Param("courseId") Long courseId);
}