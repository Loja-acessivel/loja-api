BEGIN;

-- Estrutura para bancos novos. Cada unidade comprada gera uma linha.
CREATE TABLE IF NOT EXISTS pedido (
    id             BIGSERIAL PRIMARY KEY,
    comprador_id   BIGINT NOT NULL REFERENCES usuario(id) ON DELETE RESTRICT,
    produto_id     BIGINT NOT NULL REFERENCES produto(id) ON DELETE RESTRICT,
    status         VARCHAR(20) NOT NULL DEFAULT 'recebido'
                   CHECK (status IN ('recebido', 'em_contato', 'concluido', 'cancelado')),
    total          NUMERIC(12,2) NOT NULL CHECK (total >= 0),
    criado_em      TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_pedido_comprador
    ON pedido(comprador_id);

CREATE INDEX IF NOT EXISTS idx_pedido_produto
    ON pedido(produto_id);

CREATE INDEX IF NOT EXISTS idx_pedido_criado_em
    ON pedido(criado_em DESC);

COMMIT;
