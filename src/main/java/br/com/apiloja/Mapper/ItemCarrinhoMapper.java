package br.com.apiloja.Mapper;

import br.com.apiloja.Dto.ItemCarrinho.ItemCarrinhoRequestDTO;
import br.com.apiloja.Dto.ItemCarrinho.ItemCarrinhoResponseDTO;
import br.com.apiloja.Model.ItemCarrinho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemCarrinhoMapper extends BaseMapper<ItemCarrinhoRequestDTO, ItemCarrinhoResponseDTO, ItemCarrinho>{
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "precoUnitario", ignore = true) // Será preenchido no Service com o preço do Produto
    @Mapping(target = "subtotal", ignore = true)      // Calculado automaticamente pelo banco de dados
    ItemCarrinho toEntity(ItemCarrinhoRequestDTO dto);

    @Override
    ItemCarrinhoResponseDTO toResponse(ItemCarrinho entity);
}
