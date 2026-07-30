package br.com.apiloja.Service;


import br.com.apiloja.Dto.Vendedor.VendedorRequestDTO;
import br.com.apiloja.Dto.Vendedor.VendedorResponseDTO;
import br.com.apiloja.Mapper.VendedorMapper;
import br.com.apiloja.Model.Vendedor;
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
public class VendedorService {
    private final VendedorRepository repo;
    private final UsuarioRepository usuarioRepository;
    private final VendedorMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public VendedorResponseDTO inserir(VendedorRequestDTO dto){
        validarCadastro(dto);
        dto.setEmail(normalizarEmail(dto.getEmail()));
        dto.setSenha(passwordEncoder.encode(dto.getSenha()));
        Vendedor vend = mapper.toEntity(dto);
        vend.setCriadoEm(java.time.LocalDateTime.now());
        vend.setAtualizadoEm(java.time.LocalDateTime.now());
        if (vend.getStatus() == null) {
            vend.setStatus("ativo");
        }
        repo.save(vend);
        return mapper.toResponse(vend);
    }

    public List<VendedorResponseDTO> buscarTodos(){
        List<Vendedor> vendedor = repo.findAll();
        return mapper.toResponseList(vendedor);
    }

    public VendedorResponseDTO buscarPorId(Long id){
        Vendedor vendedor = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado com o ID: " + id));;
        return mapper.toResponse(vendedor);
    }


    public void deletar(Long id){
        Vendedor vendedor = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(""));
        repo.delete(vendedor);
    }

    public VendedorResponseDTO atualizar(Long id, VendedorRequestDTO dto) {
        Vendedor vendedor = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado com o ID: " + id));

        vendedor.setNome(dto.getNome());
        vendedor.setEmail(dto.getEmail());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            vendedor.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        vendedor.setCpfCnpj(dto.getCpfCnpj());
        vendedor.setTelefone(dto.getTelefone());
        vendedor.setAvaliacao(dto.getAvaliacao());
        vendedor.setAtualizadoEm(java.time.LocalDateTime.now());
        repo.save(vendedor);
        return mapper.toResponse(vendedor);
    }

    private void validarCadastro(VendedorRequestDTO dto) {
        String email = normalizarEmail(dto.getEmail());
        if (dto.getNome() == null || dto.getNome().isBlank()
                || email.isBlank()
                || dto.getSenha() == null || dto.getSenha().isBlank()
                || dto.getCpfCnpj() == null || dto.getCpfCnpj().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nome, e-mail, senha e CPF/CNPJ são obrigatórios."
            );
        }
        if (repo.existsByEmailIgnoreCase(email) || usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma conta com este e-mail.");
        }
    }

    private String normalizarEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }
}
