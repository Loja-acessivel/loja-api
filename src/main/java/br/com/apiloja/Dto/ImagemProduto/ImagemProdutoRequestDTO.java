package br.com.apiloja.Dto.ImagemProduto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImagemProdutoRequestDTO {

    private Long produtoId;
    private MultipartFile foto;
    private Short ordem = 0;
    private Boolean principal = false;
}