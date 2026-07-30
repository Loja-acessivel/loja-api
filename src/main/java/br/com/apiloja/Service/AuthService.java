package br.com.apiloja.Service;

import br.com.apiloja.Dto.Auth.LoginRequestDTO;
import br.com.apiloja.Dto.Auth.LoginResponseDTO;
import br.com.apiloja.Model.Usuario;
import br.com.apiloja.Model.Vendedor;
import br.com.apiloja.Repository.UsuarioRepository;
import br.com.apiloja.Repository.VendedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final VendedorRepository vendedorRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO autenticar(LoginRequestDTO dto) {
        String email = normalizarEmail(dto.getEmail());
        String senha = dto.getSenha();

        if (email.isBlank() || senha == null || senha.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o e-mail e a senha.");
        }

        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email).orElse(null);
        if (usuario != null && senhaValida(senha, usuario.getSenha())) {
            migrarSenhaLegada(usuario, senha);
            return new LoginResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), "comprador");
        }

        Vendedor vendedor = vendedorRepository.findByEmailIgnoreCase(email).orElse(null);
        if (vendedor != null && senhaValida(senha, vendedor.getSenha())) {
            migrarSenhaLegada(vendedor, senha);
            return new LoginResponseDTO(vendedor.getId(), vendedor.getNome(), vendedor.getEmail(), "vendedor");
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos.");
    }

    private String normalizarEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }

    private boolean senhaValida(String senhaInformada, String senhaArmazenada) {
        if (senhaArmazenada == null) {
            return false;
        }
        if (senhaArmazenada.startsWith("$2a$")
                || senhaArmazenada.startsWith("$2b$")
                || senhaArmazenada.startsWith("$2y$")) {
            return passwordEncoder.matches(senhaInformada, senhaArmazenada);
        }
        return MessageDigest.isEqual(
                senhaInformada.getBytes(StandardCharsets.UTF_8),
                senhaArmazenada.getBytes(StandardCharsets.UTF_8)
        );
    }

    private void migrarSenhaLegada(Usuario usuario, String senha) {
        if (!usuario.getSenha().startsWith("$2")) {
            usuario.setSenha(passwordEncoder.encode(senha));
            usuarioRepository.save(usuario);
        }
    }

    private void migrarSenhaLegada(Vendedor vendedor, String senha) {
        if (!vendedor.getSenha().startsWith("$2")) {
            vendedor.setSenha(passwordEncoder.encode(senha));
            vendedorRepository.save(vendedor);
        }
    }
}
