package br.com.alura.forum.domain.resposta;

import java.time.LocalDateTime;

public record DadosDetalhamentoResposta(
        String mensagem,
        LocalDateTime dataCriacao,
        String nomeAutor,
        String tituloTopico
) {
}
