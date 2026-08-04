CREATE TABLE prontuarios (
    id BIGSERIAL PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    consulta_id BIGINT,
    anotacoes TEXT,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_paciente FOREIGN KEY (paciente_id) REFERENCES pacientes(id),
    CONSTRAINT fk_consulta FOREIGN KEY (consulta_id) REFERENCES consultas(id)
);


