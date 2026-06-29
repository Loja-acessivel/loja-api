package br.com.apiloja.Mapper;

import br.com.apiloja.Dto.Vendedor.VendedorRequestDTO;
import br.com.apiloja.Dto.Vendedor.VendedorResponseDTO;
import br.com.apiloja.Model.Vendedor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface VendedorMapper extends BaseMapper<VendedorRequestDTO, VendedorResponseDTO, Vendedor>{

}
