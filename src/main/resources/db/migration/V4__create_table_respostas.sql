CREATE TABLE respostas (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    mensagem TEXT NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    topico_id BIGINT,
    autor_id BIGINT,
    CONSTRAINT fk_resposta_topico FOREIGN KEY (topico_id) REFERENCES topicos(id),
    CONSTRAINT fk_resposta_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);
