-- Execute este script uma vez no banco Aiven existente.

ALTER TABLE imagem_produto
    ADD COLUMN IF NOT EXISTS cloudinary_public_id TEXT;

CREATE UNIQUE INDEX IF NOT EXISTS idx_imagem_cloudinary_public_id
    ON imagem_produto(cloudinary_public_id);

-- Registros antigos, cujos arquivos não estão no Cloudinary, permanecerão com NULL.
-- Após migrar ou remover esses registros, execute:
-- ALTER TABLE imagem_produto
--     ALTER COLUMN cloudinary_public_id SET NOT NULL;
