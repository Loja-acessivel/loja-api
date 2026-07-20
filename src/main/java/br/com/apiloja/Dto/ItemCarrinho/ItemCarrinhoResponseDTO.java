package br.com.apiloja.Dto.ItemCarrinho;


import lombok.Getter;

import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemCarrinhoResponseDTO {

    private Long id;
    private Long carrinhoId;
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;
}