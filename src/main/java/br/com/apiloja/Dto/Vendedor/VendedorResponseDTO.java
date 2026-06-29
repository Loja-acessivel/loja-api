package br.com.apiloja.Dto.Vendedor;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class VendedorResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpfCnpj;
    private String telefone;
    private String status;
    private BigDecimal avaliacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime criadoEm;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime atualizadoEm;

    public VendedorResponseDTO(Long id, String nome, String email, String senha, String cpfCnpj,
                               String telefone, String status, BigDecimal avaliacao,
                               LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.status = status;
        this.avaliacao = avaliacao;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
}