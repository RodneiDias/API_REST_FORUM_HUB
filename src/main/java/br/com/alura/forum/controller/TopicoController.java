package br.com.alura.forum.controller;

import br.com.alura.forum.domain.curso.Curso;
import br.com.alura.forum.domain.topico.*;
import br.com.alura.forum.domain.curso.CursoRepository;
import br.com.alura.forum.domain.usuario.Usuario;
import br.com.alura.forum.domain.usuario.UsuarioRepository;
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

import java.security.Principal;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/cursos/{idCurso}/topicos")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tópicos retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Proibido"),
        @ApiResponse(responseCode = "404", description = "Não encontrado")
})
public class TopicoController {

    private static final String TOPICO_URI_PATTERN = "/topicos/{idTopico}";

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(
            @PathVariable Long idCurso, // Usando @PathVariable para capturar o cursoId da URL
            @RequestBody @Valid DadosCadastroTopico dados,
            UriComponentsBuilder uriComponentsBuilder,
            Principal principal) {
        Usuario autor = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        Curso curso = cursoRepository.findById(idCurso) // Usando o idCurso capturado da URL
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
        Topico topico = new Topico(dados, autor, curso);
        repository.save(topico);

        var uri = uriComponentsBuilder.path(TOPICO_URI_PATTERN).buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        Page<Topico> topicos = repository.findAll(paginacao);
        Page<DadosListagemTopico> dadosListagem = topicos.map(DadosListagemTopico::new);
        return ResponseEntity.ok(dadosListagem);
    }
    @GetMapping("/{idTopico}")
    public ResponseEntity<DadosDetalhamentoTopico> listarPorId(
            @PathVariable Long idCurso,
            @PathVariable Long idTopico) {

        // Verifica se o curso existe
        cursoRepository.findById(idCurso)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        Topico topico = repository.findById(idTopico)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }
    @PutMapping("/{idTopico}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(
            @PathVariable Long idTopico,
            @RequestBody @Valid DadosAtualizacaoTopico dados,
            Principal principal) {

        Topico topico = repository.findById(idTopico)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));

        if (!topico.getAutor().getEmail().equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        topico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{idTopico}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long idTopico, Principal principal) {
        Topico topico = repository.findById(idTopico)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));

        if (!topico.getAutor().getEmail().equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        repository.delete(topico);
        return ResponseEntity.noContent().build();
    }
}
