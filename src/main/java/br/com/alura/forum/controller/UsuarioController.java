package br.com.alura.forum.controller;

import br.com.alura.forum.domain.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {
        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        Usuario usuario = new Usuario();
        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setSenha(senhaCriptografada);
        usuario.setRoles(Set.of(dados.role() != null ? dados.role() : "ROLE_USER")); // Definindo role
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<DadosListarUsuario>> listar() {
        List<DadosListarUsuario> usuarios = usuarioRepository.findAll().stream().map(DadosListarUsuario::new).toList();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<DadosDetalhamentoUsuario> detalhar(@PathVariable Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @PutMapping("/{idUsuario}")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarUsuario dados) {
        var usuario = usuarioRepository.getReferenceById(dados.id());
        usuario.atualizarInformacoes(dados, passwordEncoder);

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> deletar(@PathVariable Long idUsuario) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(idUsuario);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuarioRepository.delete(optionalUsuario.get());
        return ResponseEntity.noContent().build();
    }
}
//    @PutMapping("/{idUsuario}")
//    public ResponseEntity<Void> atualizar(@PathVariable Long idUsuario, @RequestBody @Valid DadosAtualizarUsuario dados) {
//        Optional<Usuario> optionalUsuario = usuarioRepository.findById(idUsuario);
//        if (optionalUsuario.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Usuario usuario = optionalUsuario.get();
//        usuario.setNome(dados.nome());
//        usuario.setEmail(dados.email());
//
//        if (dados.senha() != null && !dados.senha().isBlank()) {
//            String senhaCriptografada = passwordEncoder.encode(dados.senha());
//            usuario.setSenha(senhaCriptografada);
//        }
//
//        // Atualização das roles
//        Set<String> roles = new HashSet<>();
//        if (dados.role() != null && !dados.role().isBlank()) {
//            roles.add(dados.role());
//        } else {
//            roles.add("ROLE_USER"); // Adicione a role padrão se nenhuma for especificada
//        }
//        usuario.setRoles(roles);
//
//        usuarioRepository.save(usuario);
//        return ResponseEntity.ok().build();
//    }


//    @PutMapping("/{idUsuario}")
//    public ResponseEntity<Void> atualizar(@PathVariable Long idUsuario, @RequestBody @Valid DadosAtualizarUsuario dados) {
//        Optional<Usuario> optionalUsuario = usuarioRepository.findById(idUsuario);
//        if (optionalUsuario.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Usuario usuario = optionalUsuario.get();
//        System.out.println("Dados recebidos para atualização: " + dados);
//
//        usuario.setNome(dados.nome());
//        System.out.println("Nome atualizado: " + usuario.getNome());
//
//        usuario.setEmail(dados.email());
//        System.out.println("Email atualizado: " + usuario.getEmail());
//
//        if (dados.senha() != null && !dados.senha().isBlank()) {
//            String senhaCriptografada = passwordEncoder.encode(dados.senha());
//            usuario.setSenha(senhaCriptografada);
//            System.out.println("Senha atualizada.");
//        }
//
//        if (dados.role() != null && !dados.role().isBlank()) {
//            usuario.setRoles(Set.of(dados.role()));
//            System.out.println("Role atualizada: " + dados.role());
//        } else {
//            usuario.setRoles(Set.of("ROLE_USER"));
//            System.out.println("Role padrão atribuída: ROLE_USER");
//        }
//
//        usuarioRepository.save(usuario);
//        System.out.println("Usuário atualizado: " + usuario);
//
//        return ResponseEntity.ok().build();
//    }



