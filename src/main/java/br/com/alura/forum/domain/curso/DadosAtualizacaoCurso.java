package br.com.alura.forum.domain.curso;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoCurso(
        @NotBlank String nome,
        @NotBlank String categoria) {
}
