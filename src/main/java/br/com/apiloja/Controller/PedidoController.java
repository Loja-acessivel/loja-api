package br.com.apiloja.Controller;

import br.com.apiloja.Dto.Pedido.PedidoRequestDTO;
import br.com.apiloja.Dto.Pedido.PedidoResponseDTO;
import br.com.apiloja.Dto.Pedido.PedidoVendedorResponseDTO;
import br.com.apiloja.Service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<List<PedidoResponseDTO>> criar(@RequestBody PedidoRequestDTO dto) {
        return ResponseEntity.status(201).body(pedidoService.criar(dto));
    }

    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<PedidoVendedorResponseDTO>> buscarPorVendedor(
            @PathVariable Long vendedorId
    ) {
        return ResponseEntity.ok(pedidoService.buscarPorVendedor(vendedorId));
    }
}
