package br.com.alura.forum.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosCadastroUsuario(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8) String senha,
        String role // Adicionado campo para role
) {
}
