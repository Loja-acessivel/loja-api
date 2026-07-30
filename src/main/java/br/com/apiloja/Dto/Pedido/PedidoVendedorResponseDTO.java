package br.com.apiloja.Dto.Pedido;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PedidoVendedorResponseDTO {
    private Long pedidoId;
    private String compradorNome;
    private String compradorEmail;
    private String status;
    private BigDecimal subtotalVendedor;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime criadoEm;

    private List<ItemPedidoResponseDTO> itens;
}
