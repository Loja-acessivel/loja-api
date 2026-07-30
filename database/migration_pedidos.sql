BEGIN;

-- Pedido confirmado pelo comprador. O e-mail e o nome são cópias do
-- momento da compra, portanto continuam disponíveis mesmo se o perfil mudar.
CREATE TABLE IF NOT EXISTS pedido (
    id                BIGSERIAL PRIMARY KEY,
    usuario_id        BIGINT NOT NULL REFERENCES usuario(id) ON DELETE RESTRICT,
    comprador_nome    VARCHAR(100) NOT NULL,
    comprador_email   VARCHAR(150) NOT NULL,
    status            VARCHAR(20) NOT NULL DEFAULT 'recebido'
                      CHECK (status IN ('recebido', 'em_contato', 'concluido', 'cancelado')),
    total             NUMERIC(12,2) NOT NULL CHECK (total >= 0),
    criado_em         TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em     TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Mantém uma cópia do nome e do preço do produto na data do pedido.
-- produto_id pode ficar nulo se o produto for excluído futuramente.
CREATE TABLE IF NOT EXISTS item_pedido (
    id                BIGSERIAL PRIMARY KEY,
    pedido_id         BIGINT NOT NULL REFERENCES pedido(id) ON DELETE CASCADE,
    produto_id        BIGINT REFERENCES produto(id) ON DELETE SET NULL,
    vendedor_id       BIGINT NOT NULL REFERENCES vendedor(id) ON DELETE RESTRICT,
    produto_nome      VARCHAR(200) NOT NULL,
    quantidade        INTEGER NOT NULL CHECK (quantidade > 0),
    preco_unitario    NUMERIC(12,2) NOT NULL CHECK (preco_unitario >= 0),
    subtotal          NUMERIC(12,2) NOT NULL CHECK (subtotal >= 0)
);

CREATE INDEX IF NOT EXISTS idx_pedido_usuario
    ON pedido(usuario_id);

CREATE INDEX IF NOT EXISTS idx_pedido_criado_em
    ON pedido(criado_em DESC);

CREATE INDEX IF NOT EXISTS idx_item_pedido_pedido
    ON item_pedido(pedido_id);

CREATE INDEX IF NOT EXISTS idx_item_pedido_vendedor
    ON item_pedido(vendedor_id, pedido_id);

COMMIT;
