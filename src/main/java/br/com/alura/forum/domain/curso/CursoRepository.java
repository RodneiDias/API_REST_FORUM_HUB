package br.com.alura.forum.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    // Buscar curso pelo nome
    Optional<Curso> findByNome(String nome);

    // Buscar cursos pela categoria
    List<Curso> findByCategoria(String categoria);

    // Buscar curso pelo nome e categoria
    Optional<Curso> findByNomeAndCategoria(String nome, String categoria);
}
