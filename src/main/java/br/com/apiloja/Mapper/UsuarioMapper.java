package br.com.apiloja.Mapper;

import br.com.apiloja.Dto.UsuarioRequestDTO;
import br.com.apiloja.Dto.UsuarioResponseDTO;
import br.com.apiloja.Model.Usuario;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends BaseMapper<UsuarioRequestDTO,UsuarioResponseDTO, Usuario>{

}
