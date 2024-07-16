package br.com.alura.forum.domain.resposta;

import java.time.LocalDateTime;

public record DadosListagemResposta(
        String mensagem,
        LocalDateTime dataCriacao,
        String autorNome
) {
}
