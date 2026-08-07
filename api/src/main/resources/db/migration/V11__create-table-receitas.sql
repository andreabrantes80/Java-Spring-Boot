CREATE TABLE receitas (
    id BIGSERIAL PRIMARY KEY,
    medicamento VARCHAR(255) NOT NULL,
    dosagem VARCHAR(255),
    instrucoes TEXT,
    prontuario_id BIGINT NOT NULL,
    CONSTRAINT fk_receita_prontuario FOREIGN KEY (prontuario_id) REFERENCES prontuarios(id)
);



