package br.com.apiloja.Controller;

import br.com.apiloja.Dto.UsuarioRequestDTO;
import br.com.apiloja.Dto.UsuarioResponseDTO;
import br.com.apiloja.Service.ProdutoService;
import br.com.apiloja.Service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    /**
     * POST -> INSERE NO BANCO
     * GET -> BUSCA NO BANCO
     * DELETE -> EXCLUI NO BANCO
     * PATCH -> ATUALIZA NO BANCO
     * PUT -> ATUALIZAM
     */

    private final UsuarioService serviceUser;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> inserirUsuario(@RequestBody UsuarioRequestDTO dto){
        UsuarioResponseDTO userRespose = serviceUser.inserir(dto);
        return ResponseEntity.status(201).body(userRespose);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> buscarTodosUsuarios() {
        List<UsuarioResponseDTO> listaUsuarios = serviceUser.buscarTodos();
        return ResponseEntity.ok(listaUsuarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id){
        serviceUser.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id,@RequestBody UsuarioRequestDTO dto){
        UsuarioResponseDTO userRespose = serviceUser.atualizar(id, dto);
        return ResponseEntity.status(201).body(userRespose);
    }

}
