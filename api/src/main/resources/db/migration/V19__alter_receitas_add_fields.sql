-- V19__alter_receitas_add_fields.sql

-- adiciona coluna frequencia
ALTER TABLE receitas ADD COLUMN IF NOT EXISTS frequencia VARCHAR(100);

-- adiciona coluna data_prescricao com default
ALTER TABLE receitas ADD COLUMN IF NOT EXISTS data_prescricao DATE DEFAULT CURRENT_DATE;

-- adiciona coluna medico_id
ALTER TABLE receitas ADD COLUMN IF NOT EXISTS medico_id BIGINT;

-- adiciona FK para medico
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'fk_receita_medico'
          AND table_name = 'receitas'
    ) THEN
        ALTER TABLE receitas
            ADD CONSTRAINT fk_receita_medico FOREIGN KEY (medico_id) REFERENCES medicos(id);
    END IF;
END $$;








