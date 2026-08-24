CREATE TABLE IF NOT EXISTS vacinas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_aplicacao DATE NOT NULL,
    lote VARCHAR(50),
    prontuario_id BIGINT NOT NULL,
    CONSTRAINT fk_prontuario_vacina FOREIGN KEY (prontuario_id) REFERENCES prontuarios(id)
);







