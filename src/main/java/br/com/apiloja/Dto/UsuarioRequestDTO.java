package br.com.apiloja.Dto;

import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/*é o que o usuario envia*/
@Data
public class UsuarioRequestDTO {
    private String cpf;
    private  String email;
    private String nome;
    private String senhaHash;
    private String telefone;
    private String endereco;

}
