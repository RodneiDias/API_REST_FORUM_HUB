package br.com.alura.forum.controller;

import br.com.alura.forum.domain.curso.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/cursos")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de cursos retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Proibido"),
        @ApiResponse(responseCode = "404", description = "Não encontrado")
})
public class CursoController {

    private static final String CURSO_URI_PATTERN = "/cursos/{idCurso}";

    @Autowired
    private CursoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoCurso> cadastrar(
            @RequestBody @Valid DadosCadastroCurso dados,
            UriComponentsBuilder uriComponentsBuilder) {

        Curso curso = new Curso(dados.nome(), dados.categoria());
        repository.save(curso);

        var uri = uriComponentsBuilder.path(CURSO_URI_PATTERN).buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoCurso(curso.getId(), curso.getNome(), curso.getCategoria()));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCurso>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        Page<Curso> cursos = repository.findAll(paginacao);
        Page<DadosListagemCurso> dadosListagem = cursos.map(curso -> new DadosListagemCurso(curso.getId(), curso.getNome(), curso.getCategoria()));
        return ResponseEntity.ok(dadosListagem);
    }

    @GetMapping("/{idCurso}")
    public ResponseEntity<DadosDetalhamentoCurso> detalhar(@PathVariable Long idCurso) {
        Curso curso = repository.findById(idCurso)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso.getId(), curso.getNome(), curso.getCategoria()));
    }

    @PutMapping("/{idCurso}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoCurso> atualizar(
            @PathVariable Long idCurso,
            @RequestBody @Valid DadosAtualizacaoCurso dados) {

        Curso curso = repository.findById(idCurso)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        curso.setNome(dados.nome());
        curso.setCategoria(dados.categoria());
        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso.getId(), curso.getNome(), curso.getCategoria()));
    }

    @DeleteMapping("/{idCurso}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long idCurso) {
        Curso curso = repository.findById(idCurso)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        repository.delete(curso);
        return ResponseEntity.noContent().build();
    }
}