package br.com.apiloja.Service;

import br.com.apiloja.Dto.ImagemProduto.ImagemProdutoRequestDTO;
import br.com.apiloja.Dto.ImagemProduto.ImagemProdutoResponseDTO;
import br.com.apiloja.Mapper.ImagemProdutoMapper;
import br.com.apiloja.Model.ImagemProduto;
import br.com.apiloja.Model.Produto;
import br.com.apiloja.Repository.ImagemProdutoRepository;
import br.com.apiloja.Repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagemProdutoService {

    private final ImagemProdutoRepository imagemRepo;
    private final ProdutoRepository produtoRepo;
    private final UploadArquivoService uploadService;
    private final ImagemProdutoMapper mapper;

    @Transactional
    public ImagemProdutoResponseDTO vincularImagemAoProduto(ImagemProdutoRequestDTO dto) {
        Produto produto = produtoRepo.findById(dto.getProdutoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        if (Boolean.TRUE.equals(dto.getPrincipal())) {
            imagemRepo.findByProdutoIdAndPrincipalTrue(dto.getProdutoId())
                    .ifPresent(imagemPrincipal -> {
                        imagemPrincipal.setPrincipal(false);
                        imagemRepo.saveAndFlush(imagemPrincipal);
                    });
        }

        UploadResultado upload = uploadService.salvarArquivo(dto.getFoto());

        try {
            ImagemProduto novaImagem = mapper.toEntity(dto);
            novaImagem.setProduto(produto);
            novaImagem.setUrl(upload.url());
            novaImagem.setCloudinaryPublicId(upload.publicId());
            novaImagem.setCriadoEm(LocalDateTime.now());
            return mapper.toResponse(imagemRepo.save(novaImagem));
        } catch (RuntimeException erroPersistencia) {
            try {
                uploadService.excluirArquivo(upload.publicId());
            } catch (RuntimeException erroLimpeza) {
                erroPersistencia.addSuppressed(erroLimpeza);
            }
            throw erroPersistencia;
        }
    }

    @Transactional
    public void excluirImagem(Long id) {
        ImagemProduto imagem = imagemRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Imagem do produto não encontrada com o ID: " + id));

        uploadService.excluirArquivo(imagem.getCloudinaryPublicId());
        imagemRepo.delete(imagem);
    }

    public List<ImagemProdutoResponseDTO> buscarTodos() {
        return mapper.toResponseList(imagemRepo.findAll());
    }

    public List<ImagemProdutoResponseDTO> buscarPorProduto(Long produtoId) {
        return mapper.toResponseList(imagemRepo.findByProdutoId(produtoId));
    }
}
