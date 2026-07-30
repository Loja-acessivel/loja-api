package br.com.apiloja.Dto.Pedido;

import lombok.Data;

@Data
public class ItemPedidoRequestDTO {
    private Long produtoId;
    private Integer quantidade;
}
