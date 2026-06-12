package br.com.apiloja.Service;

import br.com.apiloja.Dto.UsuarioRequestDTO;
import br.com.apiloja.Dto.UsuarioResponseDTO;
import br.com.apiloja.Mapper.UsuarioMapper;
import br.com.apiloja.Model.Usuario;
import br.com.apiloja.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository repo;
    private final UsuarioMapper mapper;

    public UsuarioResponseDTO inserir(UsuarioRequestDTO dto){
        Usuario user = mapper.toEntity(dto);
        repo.save(user);

        return mapper.toResponse(user);
    }
}
