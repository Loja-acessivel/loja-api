BEGIN;

-- Migração da estrutura antiga (pedido + item_pedido) para uma única tabela.
-- Cada unidade de item_pedido vira uma linha independente em pedido.
CREATE TABLE pedido_novo (
    id             BIGSERIAL PRIMARY KEY,
    comprador_id   BIGINT NOT NULL REFERENCES usuario(id) ON DELETE RESTRICT,
    produto_id     BIGINT NOT NULL REFERENCES produto(id) ON DELETE RESTRICT,
    status         VARCHAR(20) NOT NULL DEFAULT 'recebido'
                   CHECK (status IN ('recebido', 'em_contato', 'concluido', 'cancelado')),
    total          NUMERIC(12,2) NOT NULL CHECK (total >= 0),
    criado_em      TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em  TIMESTAMP NOT NULL DEFAULT NOW()
);

INSERT INTO pedido_novo (
    comprador_id,
    produto_id,
    status,
    total,
    criado_em,
    atualizado_em
)
SELECT
    p.usuario_id,
    ip.produto_id,
    p.status,
    ip.preco_unitario,
    p.criado_em,
    p.atualizado_em
FROM pedido p
JOIN item_pedido ip ON ip.pedido_id = p.id
CROSS JOIN LATERAL generate_series(1, ip.quantidade)
WHERE ip.produto_id IS NOT NULL;

DROP TABLE item_pedido;
DROP TABLE pedido;

ALTER TABLE pedido_novo RENAME TO pedido;
ALTER SEQUENCE pedido_novo_id_seq RENAME TO pedido_id_seq;

CREATE INDEX idx_pedido_comprador
    ON pedido(comprador_id);

CREATE INDEX idx_pedido_produto
    ON pedido(produto_id);

CREATE INDEX idx_pedido_criado_em
    ON pedido(criado_em DESC);

COMMIT;
