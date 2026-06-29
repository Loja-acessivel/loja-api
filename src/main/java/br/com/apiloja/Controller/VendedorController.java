package br.com.apiloja.Controller;

import br.com.apiloja.Dto.Vendedor.VendedorRequestDTO;
import br.com.apiloja.Dto.Vendedor.VendedorResponseDTO;
import br.com.apiloja.Service.VendedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/vendedor")
@RequiredArgsConstructor
public class VendedorController {
    private final VendedorService serviceVendedor;

    @PostMapping // Quando dispararem um POST para /api/vendedores, este método será chamado
    public ResponseEntity<VendedorResponseDTO> inserirVendedor(@RequestBody VendedorRequestDTO dto) {
        VendedorResponseDTO vendedorResponse = serviceVendedor.inserir(dto);
        return ResponseEntity.status(201).body(vendedorResponse);
    }

    @GetMapping
    public ResponseEntity<List<VendedorResponseDTO>> buscarTodosVendedores() {
        List<VendedorResponseDTO> listaVendedores = serviceVendedor.buscarTodos();
        return ResponseEntity.ok(listaVendedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorResponseDTO> buscarVendedorPorId(@PathVariable Long id) {

        VendedorResponseDTO vendedor = serviceVendedor.buscarPorId(id);
        return ResponseEntity.ok(vendedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirVendedor(@PathVariable Long id) {
        serviceVendedor.deletar(id);

        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<VendedorResponseDTO> atualizarVendedor(@PathVariable Long id, @RequestBody VendedorRequestDTO dto) {
        VendedorResponseDTO vendedorResponse = serviceVendedor.atualizar(id, dto);
        return ResponseEntity.status(201).body(vendedorResponse);
    }
}
