package br.com.apiloja.Service;

import br.com.apiloja.Dto.Produto.ProdutoRequestDTO;
import br.com.apiloja.Dto.Produto.ProdutoResponseDTO;
import br.com.apiloja.Mapper.ProdutoMapper;
import br.com.apiloja.Model.Produto;
import br.com.apiloja.Repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProdutoService {
    private final ProdutoRepository repo;
    private final ProdutoMapper mapper;

    public ProdutoResponseDTO inserir(ProdutoRequestDTO prod){
        Produto produto = mapper.toEntity(prod);
        repo.save(produto);
        return mapper.toResponse(produto);
    }
}
