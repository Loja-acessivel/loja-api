package br.com.apiloja.Mapper;

import br.com.apiloja.Dto.UsuarioRequestDTO;
import br.com.apiloja.Dto.UsuarioResponseDTO;
import br.com.apiloja.Model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper implements BaseMapper<UsuarioRequestDTO,UsuarioResponseDTO, Usuario>{

    @Override
    public Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario= new Usuario();

        usuario.setCpf(dto.getCpf());
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setEndereco(dto.getEndereco());
        usuario.setSenhaHash(dto.getSenhaHash());
        return usuario;
    }

    @Override
    public UsuarioResponseDTO toResponse(Usuario usuario) {
       return new UsuarioResponseDTO(
               usuario.getId(),
               usuario.getNome(),
               usuario.getEmail(),
               usuario.getSenhaHash(),
               usuario.getCpf(),
               usuario.getTelefone(),
               usuario.getEndereco(),
               usuario.getCriadoEm(),
               usuario.getAtualizadoEm()
       );
    }

}
