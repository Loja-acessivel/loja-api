package br.com.apiloja.Mapper;


import br.com.apiloja.Dto.Carrinho.CarrinhoRequestDTO;
import br.com.apiloja.Dto.Carrinho.CarrinhoResponseDTO;
import br.com.apiloja.Model.Carrinho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarrinhoMapper extends BaseMapper<CarrinhoRequestDTO, CarrinhoResponseDTO, Carrinho>{
    @Override
    @Mapping(target = "usuarioId", source = "usuario.id") // <- Mapeia o id de dentro do objeto usuario para o usuarioId do DTO
    CarrinhoResponseDTO toResponse(Carrinho entity);
}
