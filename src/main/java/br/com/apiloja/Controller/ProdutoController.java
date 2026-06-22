package br.com.apiloja.Controller;

import br.com.apiloja.Dto.Produto.ProdutoRequestDTO;
import br.com.apiloja.Dto.Produto.ProdutoResponseDTO;
import br.com.apiloja.Dto.UsuarioResponseDTO;
import br.com.apiloja.Service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService serviceProd;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> inserirProduto(@RequestBody ProdutoRequestDTO produto){
        ProdutoResponseDTO produtoRespose = serviceProd.inserir(produto);
        return ResponseEntity.ok(produtoRespose);
    }
}
