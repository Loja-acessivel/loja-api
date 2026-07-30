package br.com.apiloja.Service;

import br.com.apiloja.Dto.Carrinho.CarrinhoRequestDTO;
import br.com.apiloja.Dto.Carrinho.CarrinhoResponseDTO;
import br.com.apiloja.Mapper.CarrinhoMapper;
import br.com.apiloja.Model.Carrinho;
import br.com.apiloja.Model.Usuario;
import br.com.apiloja.Repository.CarrinhoRepository;
import br.com.apiloja.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CarrinhoService {
    private final CarrinhoRepository repo;
    private final UsuarioRepository usuarioRepo;
    private final CarrinhoMapper mapper;

    public CarrinhoResponseDTO inserir(CarrinhoRequestDTO dto) {
        if (dto.getUsuarioId() == null) {
            throw new IllegalArgumentException("O usuarioId é obrigatório");
        }

        Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuário não encontrado com o ID: " + dto.getUsuarioId()));

        Carrinho carrinho = mapper.toEntity(dto);
        carrinho.setUsuario(usuario);
        carrinho.setStatus(dto.getStatus() == null || dto.getStatus().isBlank()
                ? "aberto"
                : dto.getStatus());
        carrinho.setTotal(BigDecimal.ZERO);
        carrinho.setCriadoEm(LocalDateTime.now());
        carrinho.setAtualizadoEm(LocalDateTime.now());
        repo.save(carrinho);
        return mapper.toResponse(carrinho);
    }

    public List<CarrinhoResponseDTO> buscarTodos() {
        return mapper.toResponseList(repo.findAll());
    }

    public CarrinhoResponseDTO buscarPorId(Long id) {
        Carrinho carrinho = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Carrinho não encontrado com o ID: " + id));
        return mapper.toResponse(carrinho);
    }

    public void deletar(Long id) {
        Carrinho carrinho = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Carrinho não encontrado com o ID: " + id));
        repo.delete(carrinho);
    }

    public CarrinhoResponseDTO atualizar(Long id, CarrinhoRequestDTO dto) {
        Carrinho carrinho = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Carrinho não encontrado com o ID: " + id));
        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            carrinho.setStatus(dto.getStatus());
        }
        carrinho.setAtualizadoEm(LocalDateTime.now());
        repo.save(carrinho);
        return mapper.toResponse(carrinho);
    }
}
