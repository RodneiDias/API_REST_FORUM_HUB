package br.com.alura.forum.domain.usuario;

public record DadosAtualizarUsuario(
        Long id,
        String nome,
        String email,
        String senha,
        String role
) {
    public DadosAtualizarUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getRoles().isEmpty() ? null : usuario.getRoles().iterator().next());
    }
}
