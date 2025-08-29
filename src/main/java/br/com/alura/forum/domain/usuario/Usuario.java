package br.com.alura.forum.domain.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String senha;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @JsonIgnore
    private Set<String> roles = new HashSet<>();

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
    public void atualizarInformacoes(DadosAtualizarUsuario dados, PasswordEncoder passwordEncoder) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.email() != null) {
            this.email = dados.email();
        }
        if (dados.senha() != null && !dados.senha().isBlank()) {
            String senhaCriptografada = passwordEncoder.encode(dados.senha());
            setSenha(senhaCriptografada);
            System.out.println("Senha atualizada.");
        }
        if (dados.role() != null && !dados.role().isBlank()) {
            setRoles(Set.of(dados.role()));
            System.out.println("Role atualizada: " + dados.role());
        } else {
            setRoles(Set.of("ROLE_USER"));
            System.out.println("Role padrão atribuída: ROLE_USER");
        }

    }
    @JsonIgnore
    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
