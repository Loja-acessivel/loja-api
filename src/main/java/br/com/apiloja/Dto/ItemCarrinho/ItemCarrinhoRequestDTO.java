package br.com.apiloja.Dto.ItemCarrinho;

import lombok.Data;

@Data
public class ItemCarrinhoRequestDTO {
    private Long carrinhoId;
    private Long produtoId;
    private Integer quantidade = 1;
}