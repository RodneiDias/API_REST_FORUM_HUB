package br.com.alura.forum.domain.topico;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTopico(
        Long id,
        String titulo,
        String mensagem
        ) {
}
