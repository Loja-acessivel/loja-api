package br.com.apiloja.Dto.Carrinho;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CarrinhoResponseDTO {

    private Long id;
    private Long usuarioId;
    private String status;
    private BigDecimal total;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}