package br.com.alura.forum.domain.curso;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "cursos")
@Entity(name = "Curso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String categoria;

    // Construtor adicional
    public Curso(String nome, String categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }
}
