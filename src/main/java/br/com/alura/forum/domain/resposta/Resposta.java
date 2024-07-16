package br.com.alura.forum.domain.resposta;

import br.com.alura.forum.domain.topico.Topico;
import br.com.alura.forum.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Table(name = "respostas")
@Entity(name = "Resposta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;

    @Column(name = "dataCriacao")
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @JsonIgnoreProperties("respostas")
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    public Resposta(DadosCadastroResposta dados, Topico topico, Usuario autor) {
        this.mensagem = dados.mensagem();
        this.dataCriacao = LocalDateTime.now();
        this.topico = topico;
        this.autor = autor;
    }

    public void atualizarMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Transactional
    public void excluir() {

    }
}
