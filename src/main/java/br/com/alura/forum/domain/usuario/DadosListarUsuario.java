package br.com.alura.forum.domain.usuario;

public record DadosListarUsuario(
        Long id,
        String nome,
        String email
) {
    public DadosListarUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail());
    }
}
