package br.com.apiloja.Dto.Usuario;

import lombok.Data;

/*é o que o usuario envia*/
@Data
public class UsuarioRequestDTO {
    private String cpf;
    private  String email;
    private String nome;
    private String senha;
    private String telefone;
    private String endereco;

}
