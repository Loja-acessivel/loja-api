package br.com.apiloja.Dto.Vendedor;


import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendedorRequestDTO {
    private String nome;

    private String email;

    private String senha;

    @Column(name = "cpf_cnpj")
    private String cpfCnpj;

    private String telefone;
    private BigDecimal avaliacao;

}
