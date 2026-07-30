package br.com.apiloja.Dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String tipo;
}
