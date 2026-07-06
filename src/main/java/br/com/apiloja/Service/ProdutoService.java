package br.com.apiloja.Service;

import br.com.apiloja.Dto.Produto.ProdutoRequestDTO;
import br.com.apiloja.Dto.Produto.ProdutoResponseDTO;
import br.com.apiloja.Dto.Vendedor.VendedorResponseDTO;
import br.com.apiloja.Mapper.ProdutoMapper;
import br.com.apiloja.Model.Produto;
import br.com.apiloja.Model.Vendedor;
import br.com.apiloja.Repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public List<ProdutoResponseDTO> buscarTodos(){
        List<Produto> produtos = repo.findAll();
        return mapper.toResponseList(produtos);
    }

    public ProdutoResponseDTO buscarPorId(Long id){
        Produto produtos = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
        return mapper.toResponse(produtos);
    }

    public void deletar(Long id){
        Produto produto = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(""));
        repo.delete(produto);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto){
        Produto produto = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(""));

        produto.setNome(dto.getNome());
        produto.setCategoria(dto.getCategoria());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setEstoque(dto.getEstoque());

        repo.save(produto);
        return mapper.toResponse(produto);
    }
}
