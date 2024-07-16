package br.com.alura.forum.domain.topico;

import br.com.alura.forum.domain.resposta.Resposta;
import br.com.alura.forum.domain.usuario.Usuario;
import br.com.alura.forum.domain.curso.Curso;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor // Construtor com todos os campos para uso geral
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensagem;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private StatusTopico status = StatusTopico.NAO_RESPONDIDO;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private List<Resposta> respostas;
    private List<Resposta> respostas = new ArrayList<>();

    // Construtor para criar um tópico a partir de DadosCadastroTopico, autor e curso
    public Topico(DadosCadastroTopico dados, Usuario autor, Curso curso) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusTopico.valueOf(dados.status().toUpperCase());
        this.autor = autor;
        this.curso = curso;
    }

    public void atualizarInformacoes(DadosAtualizacaoTopico dados) {
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
    }

    @Transactional
    public void excluir() {
        this.status = StatusTopico.DELETADO;
    }
}
