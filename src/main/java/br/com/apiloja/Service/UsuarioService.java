package br.com.apiloja.Service;

import br.com.apiloja.Dto.Usuario.UsuarioRequestDTO;
import br.com.apiloja.Dto.Usuario.UsuarioResponseDTO;
import br.com.apiloja.Mapper.UsuarioMapper;
import br.com.apiloja.Model.Usuario;
import br.com.apiloja.Repository.UsuarioRepository;
import br.com.apiloja.Repository.VendedorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository repo;
    private final VendedorRepository vendedorRepository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponseDTO inserir(UsuarioRequestDTO dto){
        validarCadastro(dto);
        dto.setEmail(normalizarEmail(dto.getEmail()));
        dto.setSenha(passwordEncoder.encode(dto.getSenha()));
        Usuario user = mapper.toEntity(dto);
        user.setCriadoEm(java.time.LocalDateTime.now());
        user.setAtualizadoEm(java.time.LocalDateTime.now());
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
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        usuario.setEmail(normalizarEmail(dto.getEmail()));
        usuario.setTelefone(dto.getTelefone());
        usuario.setAtualizadoEm(java.time.LocalDateTime.now());

        repo.save(usuario);
        return mapper.toResponse(usuario);
    }

    private void validarCadastro(UsuarioRequestDTO dto) {
        String email = normalizarEmail(dto.getEmail());
        if (dto.getNome() == null || dto.getNome().isBlank()
                || email.isBlank()
                || dto.getSenha() == null || dto.getSenha().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome, e-mail e senha são obrigatórios.");
        }
        if (repo.existsByEmailIgnoreCase(email) || vendedorRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma conta com este e-mail.");
        }
    }

    private String normalizarEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }
}
