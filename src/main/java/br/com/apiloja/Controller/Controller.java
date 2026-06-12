package br.com.apiloja.Controller;

import br.com.apiloja.Dto.UsuarioRequestDTO;
import br.com.apiloja.Dto.UsuarioResponseDTO;
import br.com.apiloja.Service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class Controller {
    /**
     * POST -> INSERE NO BANCO
     * GET -> BUSCA NO BANCO
     * DELETE -> EXCLUI NO BANCO
     * PATCH -> ATUALIZA NO BANCO
     * PUT -> ATUALIZAM
     */

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> inserirUsuario(@RequestBody UsuarioRequestDTO dto){
        UsuarioResponseDTO userRespose = service.inserir(dto);
        return ResponseEntity.ok(userRespose);
    }

}
