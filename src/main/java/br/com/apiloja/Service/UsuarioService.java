package br.com.apiloja.Service;

import br.com.apiloja.Dto.Usuario.UsuarioRequestDTO;
import br.com.apiloja.Dto.Usuario.UsuarioResponseDTO;
import br.com.apiloja.Mapper.UsuarioMapper;
import br.com.apiloja.Model.Usuario;
import br.com.apiloja.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<UsuarioResponseDTO> buscarTodos(){
        List<Usuario> usuario = repo.findAll();
        return mapper.toResponseList(usuario);
    }
    public UsuarioResponseDTO buscarPorId(Long id){
        Usuario usuario = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado com o ID: " + id));;
        return mapper.toResponse(usuario);
    }

    public void deletar(Long id){
        Usuario usuario = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(""));
        repo.delete(usuario);
    }

    public UsuarioResponseDTO atualizar(Long id,UsuarioRequestDTO dto){
        Usuario usuario = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(""));

        usuario.setCpf(dto.getCpf());
        usuario.setNome(dto.getNome());
        usuario.setEndereco(dto.getEndereco());
        usuario.setSenha(dto.getSenha());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());

        repo.save(usuario);
        return mapper.toResponse(usuario);
    }
}
