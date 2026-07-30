-- Execute este script uma vez no banco existente.

ALTER TABLE imagem_produto
    ADD COLUMN IF NOT EXISTS cloudinary_public_id TEXT;

CREATE UNIQUE INDEX IF NOT EXISTS idx_imagem_cloudinary_public_id
    ON imagem_produto(cloudinary_public_id);

-- Registros antigos podem permanecer com NULL até serem migrados ou removidos.
