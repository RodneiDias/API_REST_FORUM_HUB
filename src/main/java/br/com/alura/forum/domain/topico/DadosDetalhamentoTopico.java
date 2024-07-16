package br.com.alura.forum.domain.topico;

import br.com.alura.forum.domain.topico.StatusTopico;
import br.com.alura.forum.domain.topico.Topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, String autor, StatusTopico status) {
    public DadosDetalhamentoTopico (Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getAutor().getNome(),
                topico.getStatus()
        );
    }
}