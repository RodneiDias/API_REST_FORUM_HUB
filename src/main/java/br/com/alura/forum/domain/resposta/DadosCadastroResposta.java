package br.com.alura.forum.domain.resposta;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroResposta(
        @NotBlank String mensagem
) {
}
