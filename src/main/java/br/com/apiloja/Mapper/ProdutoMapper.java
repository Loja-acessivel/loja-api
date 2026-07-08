package br.com.apiloja.Mapper;

import br.com.apiloja.Dto.Produto.ProdutoRequestDTO;
import br.com.apiloja.Dto.Produto.ProdutoResponseDTO;
import br.com.apiloja.Model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProdutoMapper extends BaseMapper<ProdutoRequestDTO, ProdutoResponseDTO, Produto> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Mapping(target = "status", ignore = true)
    Produto toEntity(ProdutoRequestDTO dto);

    @Override
    ProdutoResponseDTO toResponse(Produto entity);
}