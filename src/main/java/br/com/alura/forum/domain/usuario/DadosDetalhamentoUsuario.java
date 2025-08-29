package br.com.alura.forum.domain.usuario;

import java.util.Set;

public record DadosDetalhamentoUsuario(
        Long id,
        String nome,
        String email,
        Set<String> roles) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRoles());
    }

}
