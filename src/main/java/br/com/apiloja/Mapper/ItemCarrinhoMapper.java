package br.com.apiloja.Mapper;

import br.com.apiloja.Dto.Carrinho.CarrinhoRequestDTO;
import br.com.apiloja.Dto.Carrinho.CarrinhoResponseDTO;
import br.com.apiloja.Dto.ItemCarrinho.ItemCarrinhoRequestDTO;
import br.com.apiloja.Dto.ItemCarrinho.ItemCarrinhoResponseDTO;
import br.com.apiloja.Model.Carrinho;

public interface ItemCarrinhoMapper extends BaseMapper<ItemCarrinhoRequestDTO, ItemCarrinhoResponseDTO, Carrinho>{
}
