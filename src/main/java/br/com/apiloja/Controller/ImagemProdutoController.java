package br.com.apiloja.Controller;

import br.com.apiloja.Dto.ImagemProduto.ImagemProdutoRequestDTO;
import br.com.apiloja.Dto.ImagemProduto.ImagemProdutoResponseDTO;
import br.com.apiloja.Service.ImagemProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imagens")
@RequiredArgsConstructor
public class ImagemProdutoController {

    private final ImagemProdutoService imagemService;

    /**
     * 1. POST - Upload e Vinculação de Imagem
     * IMPORTANTE: Usamos 'consumes = MediaType.MULTIPART_FORM_DATA_VALUE'
     * e a anotação '@ModelAttribute' em vez de '@RequestBody'.
     * Isso permite que o Spring extraia o arquivo binário (MultipartFile) e os dados simples (produtoId, ordem, etc) juntos.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImagem(@ModelAttribute ImagemProdutoRequestDTO dto) {
        imagemService.vincularImagemAoProduto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Imagem vinculada com sucesso!");
    }

    /**
     * 2. GET - Buscar todas as imagens de um produto específico
     */
    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<ImagemProdutoResponseDTO>> buscarPorProduto(@PathVariable Long produtoId) {
        List<ImagemProdutoResponseDTO> imagens = imagemService.buscarPorProduto(produtoId);
        return ResponseEntity.ok(imagens);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirImagem(@PathVariable Long id) {
        imagemService.excluirImagem(id);
        return ResponseEntity.noContent().build(); // Retorna Status 204 (No Content) após exclusão
    }
}