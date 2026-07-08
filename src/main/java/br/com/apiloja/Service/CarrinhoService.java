package br.com.apiloja.Service;

import br.com.apiloja.Dto.Carrinho.CarrinhoRequestDTO;
import br.com.apiloja.Dto.Carrinho.CarrinhoResponseDTO;
import br.com.apiloja.Mapper.CarrinhoMapper;
import br.com.apiloja.Model.Carrinho;
import br.com.apiloja.Repository.CarrinhoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CarrinhoService {
    private final CarrinhoRepository repo;
    private final CarrinhoMapper mapper;

    public CarrinhoResponseDTO inserir(CarrinhoRequestDTO dto){
        Carrinho carrinho = mapper.toEntity(dto);
        carrinho.setCriadoEm(java.time.LocalDateTime.now());
        carrinho.setAtualizadoEm(java.time.LocalDateTime.now());
        repo.save(carrinho);
        return mapper.toResponse(carrinho);
    }

    public List<CarrinhoResponseDTO> buscarTodos(){
        List<Carrinho> carrinhos = repo.findAll();
        return mapper.toResponseList(carrinhos);
    }
    public CarrinhoResponseDTO buscarPorId(Long id){
        Carrinho carrinho = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado com o ID: " + id));
        return mapper.toResponse(carrinho);
    }

    public void deletar(Long id){
        Carrinho carrinho = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(""));
        repo.delete(carrinho);
    }
    public CarrinhoResponseDTO atualizar(Long id, CarrinhoRequestDTO dto) {
        Carrinho carrinho = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado com o ID: " + id));
        carrinho.setStatus(dto.getStatus());
        carrinho.setAtualizadoEm(java.time.LocalDateTime.now());
        repo.save(carrinho);
        return mapper.toResponse(carrinho);
    }
}
