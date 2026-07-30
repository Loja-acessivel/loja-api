package br.com.apiloja.Mapper;

import br.com.apiloja.Dto.ImagemProduto.ImagemProdutoRequestDTO;
import br.com.apiloja.Dto.ImagemProduto.ImagemProdutoResponseDTO;
import br.com.apiloja.Model.ImagemProduto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImagemProdutoMapper extends BaseMapper<ImagemProdutoRequestDTO, ImagemProdutoResponseDTO, ImagemProduto> {

    @Override
    @Mapping(target = "produto.id", source = "produtoId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "url", ignore = true)
    @Mapping(target = "cloudinaryPublicId", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    ImagemProduto toEntity(ImagemProdutoRequestDTO dto);

    @Override
    @Mapping(target = "produtoId", source = "produto.id")
    ImagemProdutoResponseDTO toResponse(ImagemProduto entity);
}
