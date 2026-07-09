package br.com.apiloja.Service;

import br.com.apiloja.Dto.ImagemProduto.ImagemProdutoRequestDTO;
import br.com.apiloja.Mapper.ImagemProdutoMapper;
import br.com.apiloja.Model.ImagemProduto;
import br.com.apiloja.Model.Produto;
import br.com.apiloja.Repository.ImagemProdutoRepository;
import br.com.apiloja.Repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImagemProdutoService {

    private final ImagemProdutoRepository imagemRepo;
    private final ProdutoRepository produtoRepo;
    private final UploadArquivoService uploadService;
    private final ImagemProdutoMapper mapper;

    public void vincularImagemAoProduto(ImagemProdutoRequestDTO dto) {
        ImagemProduto novaImagem = mapper.toEntity(dto);
        Produto produto = produtoRepo.findById(dto.getProdutoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        // 2. Faz o upload físico e pega a URL externa
        String urlImagem = uploadService.salvarArquivo(dto.getFoto());

        // 3. Monta o objeto para salvar no banco de dados
        novaImagem.setProduto(produto);
        novaImagem.setUrl(urlImagem);
        novaImagem.setCriadoEm(java.time.LocalDateTime.now());

        // 4. Salva no banco
        imagemRepo.save(novaImagem);
    }
}