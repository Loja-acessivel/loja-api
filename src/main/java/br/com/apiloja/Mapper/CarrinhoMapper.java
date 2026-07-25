package br.com.apiloja.Mapper;


import br.com.apiloja.Dto.Carrinho.CarrinhoRequestDTO;
import br.com.apiloja.Dto.Carrinho.CarrinhoResponseDTO;
import br.com.apiloja.Model.Carrinho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarrinhoMapper extends BaseMapper<CarrinhoRequestDTO, CarrinhoResponseDTO, Carrinho>{
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    Carrinho toEntity(CarrinhoRequestDTO dto);

    @Override
    @Mapping(target = "usuarioId", source = "usuario.id")
    CarrinhoResponseDTO toResponse(Carrinho entity);
}
