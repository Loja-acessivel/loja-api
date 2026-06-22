package br.com.apiloja.Dto.Produto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
public class ProdutoResponseDTO {
    private Long id;
    private Long vendedorId;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer estoque;
    private String categoria;
    private String status;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime criadoEm;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime atualizadoEm;
    
    public ProdutoResponseDTO(Long id, Long vendedorId, String nome, String descricao,
                              Double preco, Integer estoque, String categoria,
                              String status, LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        this.id = id;
        this.vendedorId = vendedorId;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
        this.status = status;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
}