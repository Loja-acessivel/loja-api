package br.com.apiloja.Dto.Pedido;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {
    private Long compradorId;
    private List<ItemPedidoRequestDTO> itens;
}
