package br.com.apiloja.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter @Setter
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String senhaHash;
    private String cpf;
    private String telefone;
    private String endereco;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss",timezone = "America/Sao_Paulo")
    private LocalDateTime criadoEm;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss",timezone = "America/Sao_Paulo")
    private LocalDateTime atualizadoEm;

    public UsuarioResponseDTO(Long id, String nome, String email, String senhaHash, String cpf, String telefone, String endereco, LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
}
