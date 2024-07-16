package br.com.alura.forum.controller;

import br.com.alura.forum.domain.resposta.*;
import br.com.alura.forum.domain.topico.Topico;
import br.com.alura.forum.domain.topico.TopicoRepository;
import br.com.alura.forum.domain.usuario.Usuario;
import br.com.alura.forum.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/cursos/{idCurso}/topicos/{idTopico}/respostas")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Proibido"),
        @ApiResponse(responseCode = "404", description = "Não encontrado")
})
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Operation(summary = "Cadastrar uma resposta para um tópico")
    public ResponseEntity<Resposta> cadastrar(
            @PathVariable Long idCurso,
            @PathVariable Long idTopico,
            @RequestBody @Valid DadosCadastroResposta dados,
            Principal principal) {

        // Verifica se o tópico existe e está associado ao curso
        Topico topico = topicoRepository.findById(idTopico)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));

        if (!topico.getCurso().getId().equals(idCurso)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tópico não pertence ao curso especificado");
        }

        // Verifica se o autor existe
        Usuario autor = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        // Cria e salva a nova resposta
        Resposta resposta = new Resposta(dados, topico, autor);
        respostaRepository.save(resposta);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemResposta>> listar(
            @PathVariable Long idTopico,
            @PageableDefault(size = 10, sort = {"dataCriacao"}, direction = Sort.Direction.DESC) Pageable paginacao) {

        Page<Resposta> respostas = respostaRepository.findByTopicoId(idTopico, paginacao);

        Page<DadosListagemResposta> dadosListagem = respostas.map(resposta ->
                new DadosListagemResposta(resposta.getMensagem(), resposta.getDataCriacao(), resposta.getAutor().getNome()));

        return ResponseEntity.ok(dadosListagem);
    }

    @GetMapping("/{idResposta}")
    public ResponseEntity<DadosDetalhamentoResposta> listarPorId(
            @PathVariable Long idCurso,
            @PathVariable Long idTopico,
            @PathVariable Long idResposta) {

        Topico topico = topicoRepository.findById(idTopico)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));

        Resposta resposta = respostaRepository.findById(idResposta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resposta não encontrada"));

        DadosDetalhamentoResposta detalhes = new DadosDetalhamentoResposta(
                resposta.getMensagem(),
                resposta.getDataCriacao(),
                resposta.getAutor().getNome(),
                topico.getTitulo()
        );

        return ResponseEntity.ok(detalhes);
    }

    @PutMapping("/{idResposta}")
    @Operation(summary = "Atualizar a mensagem de uma resposta")
    public ResponseEntity<DadosDetalhamentoResposta> atualizarMensagem(
            @PathVariable Long idTopico,
            @PathVariable Long idResposta,
            @RequestBody @Valid DadosAtualizacaoResposta dadosAtualizacaoResposta,
            Principal principal) {

        Resposta resposta = respostaRepository.findById(idResposta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resposta não encontrada"));

        if (!resposta.getAutor().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para atualizar esta resposta");
        }

        resposta.setMensagem(dadosAtualizacaoResposta.mensagem());
        respostaRepository.save(resposta);

        DadosDetalhamentoResposta respostaDetalhada = new DadosDetalhamentoResposta(
                resposta.getMensagem(),
                resposta.getDataCriacao(),
                resposta.getAutor().getNome(),
                resposta.getTopico().getTitulo()
        );

        return ResponseEntity.ok(respostaDetalhada);
    }

    @DeleteMapping("/{idResposta}")
    @Transactional
    public ResponseEntity<Void> excluir(
            @PathVariable Long idTopico,
            @PathVariable Long idResposta,
            Principal principal) {

        Resposta resposta = respostaRepository.findById(idResposta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resposta não encontrada"));

        if (!resposta.getAutor().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para excluir esta resposta");
        }

        respostaRepository.delete(resposta);

        return ResponseEntity.noContent().build();
    }
}

