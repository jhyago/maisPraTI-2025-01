package com.t2.apiexample.enrollment;

import com.t2.apiexample.course.Course;
import com.t2.apiexample.course.CourseRepository;
import com.t2.apiexample.enrollment.dto.EnrollmentRequest;
import com.t2.apiexample.user.User;
import com.t2.apiexample.user.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CONTROLADOR DE MATRÍCULAS (Enrollment)
 *
 * Responsabilidades:
 *  - Criar matrícula (POST /enrollments)
 *  - Listar matrículas (GET  /enrollments)
 *  - Consultas específicas (ex.: IDs de usuários por curso, com COUNT)
 *
 * Observações de design:
 *  - Retornar tipos adequados para cada cenário:
 *      • sucesso de criação → 201 + Location + corpo com recurso criado
 *      • erros de validação/negócio → 400/409 com um corpo de erro simples (Map) ou DTO próprio
 *  - Evitar lançar NullPointerException/NoSuchElementException:
 *      • trate Optional com orElse(null) + if (null) ou orElseThrow(() -> …)
 *  - Mensagens e HTTP status consistentes melhoram DX (developer experience).
 */
@Tag(name = "Enrollments")
@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentController(EnrollmentRepository enrollmentRepository,
                                UserRepository userRepository,
                                CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * CRIA UMA MATRÍCULA
     *
     * Fluxo:
     *  1) Recebe EnrollmentRequest validado (@Valid)
     *  2) Busca usuário e curso (tratando não encontrados → 400 Bad Request)
     *  3) Verifica se já existe matrícula (conflito → 409 Conflict)
     *  4) Persiste e retorna 201 Created com Location
     *
     * ATENÇÃO (erro comum corrigido abaixo):
     *  - existsByCourseIdAndUserId(courseId, userId) → a ordem dos argumentos importa!
     *    No código original a ordem estava invertida.
     */
    @PostMapping
    public ResponseEntity<?> enroll(@Valid @RequestBody EnrollmentRequest request) {
        // Busca os agregados necessários. Optional.orElse(null) evita exception e permite tratar 400.
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user == null) {
            // 400 Bad Request com corpo JSON simples explicando o problema.
            return ResponseEntity.badRequest().body(Map.of("error", "Usuário não encontrado"));
        }

        Course course = courseRepository.findById(request.getCourseId()).orElse(null);
        if (course == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Curso não encontrado"));
        }

        // Verifica duplicidade de matrícula:
        // IMPORTANTE: a assinatura sugere (courseId, userId) — confirme no repository.
        if (enrollmentRepository.existsByCourseIdAndUserId(course.getId(), user.getId())) {
            return ResponseEntity.status(409).body(Map.of("error", "Aluno já está matriculado"));
        }

        // Cria a matrícula e salva
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setUser(user);
        Enrollment saved = enrollmentRepository.save(enrollment);

        // 201 Created + Location do recurso recém-criado
        return ResponseEntity
                .created(URI.create("/enrollments/" + saved.getId()))
                .body(saved);
    }

    /**
     * LISTA TODAS AS MATRÍCULAS
     *
     * Simples pass-through para o repositório. Em APIs públicas,
     * considere paginação (Pageable) para grandes volumes.
     */
    @GetMapping
    public List<Enrollment> listAll() {
        return enrollmentRepository.findAll();
    }

    /**
     * LISTA IDS DE USUÁRIOS MATRICULADOS EM UM CURSO (NATIVO) + CONTAGEM
     *
     * Endpoint útil para relatórios/consultas leves, retornando um "envelope" JSON
     * com o ID do curso, a lista de userIds e o total.
     *
     * Exemplo de resposta:
     * {
     *   "courseId": 42,
     *   "userIds": [1, 5, 7],
     *   "count": 3
     * }
     */
    @GetMapping("/by-course/{courseId}/users")
    public Map<String, Object> listUserIdsByCourseNative(@PathVariable Long courseId) {
        List<Long> userIds = enrollmentRepository.findUserIdsByCourseNative(courseId);
        Long count = enrollmentRepository.countByCourseIdNative(courseId);

        Map<String, Object> response = new HashMap<>();
        response.put("courseId", courseId);
        response.put("userIds", userIds);
        response.put("count", count);

        return response;
    }
}