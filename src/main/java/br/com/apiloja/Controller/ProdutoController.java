package br.com.apiloja.Controller;

import br.com.apiloja.Dto.Produto.ProdutoRequestDTO;
import br.com.apiloja.Dto.Produto.ProdutoResponseDTO;
import br.com.apiloja.Dto.UsuarioResponseDTO;
import br.com.apiloja.Service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService serviceProd;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> inserirProduto(@RequestBody ProdutoRequestDTO produto){
        ProdutoResponseDTO produtoRespose = serviceProd.inserir(produto);
        return ResponseEntity.status(201).body(produtoRespose);
    }
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> buscarTodosProdutos() {
        List<ProdutoResponseDTO> listaProdutos = serviceProd.buscarTodos();
        return ResponseEntity.ok(listaProdutos);
    }

    @GetMapping("/{id}")     public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(@PathVariable Long id) {
        ProdutoResponseDTO produto = serviceProd.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        serviceProd.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoRequestDTO dto) {

        // Chama o service de produto para aplicar as atualizações
        ProdutoResponseDTO produtoResponse = serviceProd.atualizar(id, dto);
        return ResponseEntity.status(201).body(produtoResponse);
    }
}
