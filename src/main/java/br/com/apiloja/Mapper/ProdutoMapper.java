package br.com.apiloja.Mapper;

import br.com.apiloja.Dto.Produto.ProdutoRequestDTO;
import br.com.apiloja.Dto.Produto.ProdutoResponseDTO;
import br.com.apiloja.Model.Produto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProdutoMapper extends BaseMapper<ProdutoRequestDTO, ProdutoResponseDTO, Produto>{

}
