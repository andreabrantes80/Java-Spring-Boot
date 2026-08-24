CREATE TABLE IF NOT EXISTS exames (
    id BIGSERIAL PRIMARY KEY,
    tipo VARCHAR(100) NOT NULL,
    resultado TEXT NOT NULL,
    data DATE NOT NULL,
    prontuario_id BIGINT NOT NULL,
    CONSTRAINT fk_prontuario_exame FOREIGN KEY (prontuario_id) REFERENCES prontuarios(id)
);







