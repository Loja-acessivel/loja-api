package br.com.apiloja.Mapper;

import br.com.apiloja.Dto.Usuario.UsuarioRequestDTO;
import br.com.apiloja.Dto.Usuario.UsuarioResponseDTO;
import br.com.apiloja.Model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends BaseMapper<UsuarioRequestDTO,UsuarioResponseDTO, Usuario>{

}
