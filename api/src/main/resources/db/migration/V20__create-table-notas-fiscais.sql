CREATE TABLE notas_fiscais (
    id BIGSERIAL PRIMARY KEY,
    prestador_nome VARCHAR(255) NOT NULL,
    prestador_cpf_cnpj VARCHAR(20) NOT NULL,
    paciente_nome VARCHAR(255) NOT NULL,
    paciente_cpf VARCHAR(20) NOT NULL,
    descricao_servico VARCHAR(255) NOT NULL,
    valor_servico NUMERIC(10,2) NOT NULL,
    aliquota_iss NUMERIC(5,2),
    valor_imposto NUMERIC(10,2),
    data_emissao TIMESTAMP NOT NULL DEFAULT NOW(),
    prontuario_id BIGINT,
    CONSTRAINT fk_prontuario FOREIGN KEY (prontuario_id) REFERENCES prontuarios(id)
);
