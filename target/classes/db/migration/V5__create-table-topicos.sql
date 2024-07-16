CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    status VARCHAR(50) DEFAULT 'NAO_RESPONDIDO' NOT NULL,
    data_criacao DATETIME NOT NULL,
    autor_id BIGINT,
    curso_id BIGINT,
    CONSTRAINT fk_topico_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    CONSTRAINT fk_topico_curso FOREIGN KEY (curso_id) REFERENCES cursos(id)
);
