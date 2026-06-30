package br.com.apiloja.Dto.Carrinho;

import lombok.Data;

@Data
public class CarrinhoRequestDTO {

    private Long usuarioId;
    private String status;
}