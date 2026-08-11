CREATE TABLE reset_token (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);




