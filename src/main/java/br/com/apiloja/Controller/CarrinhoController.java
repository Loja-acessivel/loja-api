package br.com.apiloja.Controller;

import br.com.apiloja.Dto.Carrinho.CarrinhoRequestDTO;
import br.com.apiloja.Dto.Carrinho.CarrinhoResponseDTO;
import br.com.apiloja.Service.CarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService serviceCarrinho;

    @PostMapping
    public ResponseEntity<CarrinhoResponseDTO> inserirCarrinho(@RequestBody CarrinhoRequestDTO carrinho) {
        CarrinhoResponseDTO carrinhoResponse = serviceCarrinho.inserir(carrinho);
        return ResponseEntity.status(201).body(carrinhoResponse);
    }

    @GetMapping
    public ResponseEntity<List<CarrinhoResponseDTO>> buscarTodosCarrinhos() {
        List<CarrinhoResponseDTO> listaCarrinhos = serviceCarrinho.buscarTodos();
        return ResponseEntity.ok(listaCarrinhos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarrinhoResponseDTO> buscarCarrinhoPorId(@PathVariable Long id) {
        CarrinhoResponseDTO carrinho = serviceCarrinho.buscarPorId(id);
        return ResponseEntity.ok(carrinho);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCarrinho(@PathVariable Long id) {
        serviceCarrinho.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CarrinhoResponseDTO> atualizarCarrinho(@PathVariable Long id, @RequestBody CarrinhoRequestDTO dto) {
        // Chama o service de carrinho para aplicar as atualizações (como o status)
        CarrinhoResponseDTO carrinhoResponse = serviceCarrinho.atualizar(id, dto);
        return ResponseEntity.ok(carrinhoResponse);
    }
}