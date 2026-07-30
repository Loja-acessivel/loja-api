package br.com.apiloja.Dto.Produto;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

/* É o que o usuário/vendedor envia para cadastrar ou atualizar um produto */
@Data
public class ProdutoRequestDTO {

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Integer estoque;

    private String categoria;
    
    private Long vendedorId;
}
