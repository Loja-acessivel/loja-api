package br.com.apiloja.Dto.Pedido;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PedidoVendedorResponseDTO {
    private Long id;
    private Long compradorId;
    private String compradorNome;
    private String compradorEmail;
    private String compradorTelefone;
    private String compradorEndereco;
    private Long produtoId;
    private String produtoNome;
    private String status;
    private BigDecimal total;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime criadoEm;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime atualizadoEm;
}
