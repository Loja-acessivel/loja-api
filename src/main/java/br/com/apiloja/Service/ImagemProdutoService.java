package br.com.apiloja.Service;

import br.com.apiloja.Dto.ImagemProduto.ImagemProdutoRequestDTO;
import br.com.apiloja.Dto.ImagemProduto.ImagemProdutoResponseDTO;
import br.com.apiloja.Dto.Produto.ProdutoResponseDTO;
import br.com.apiloja.Mapper.ImagemProdutoMapper;
import br.com.apiloja.Model.ImagemProduto;
import br.com.apiloja.Model.Produto;
import br.com.apiloja.Repository.ImagemProdutoRepository;
import br.com.apiloja.Repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagemProdutoService {

    private final ImagemProdutoRepository imagemRepo;
    private final ProdutoRepository produtoRepo;
    private final UploadArquivoService uploadService;
    private final ImagemProdutoMapper mapper;

    public ImagemProdutoResponseDTO vincularImagemAoProduto(ImagemProdutoRequestDTO dto) {
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
        return mapper.toResponse(novaImagem);
    }

    @Transactional
    public void excluirImagem(Long id) {
        // 1. Busca os detalhes da imagem antes de apagar
        ImagemProduto imagem = imagemRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imagem do produto não encontrada com o ID: " + id));

        // 2. Apaga o arquivo físico do computador/servidor usando a URL salva
        // (Será necessário criar esse método auxiliar no seu UploadArquivoService)
        uploadService.excluirArquivo(imagem.getUrl());

        // 3. Deleta o registro do banco de dados
        imagemRepo.delete(imagem);
    }


    public List<ImagemProdutoResponseDTO> buscarTodos(){
        List<ImagemProduto> ImagemProdutos = imagemRepo.findAll();
        return mapper.toResponseList(ImagemProdutos);
    }

    public List<ImagemProdutoResponseDTO> buscarPorProduto(Long produtoId){
        List<ImagemProduto> imagens = imagemRepo.findByProdutoId(produtoId);

        // 3. Converte toda a lista de Entity para ResponseDTO usando o seu BaseMapper
        return mapper.toResponseList(imagens);
    }
}