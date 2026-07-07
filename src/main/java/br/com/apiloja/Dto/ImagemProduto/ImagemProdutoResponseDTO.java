package br.com.apiloja.Dto.ImagemProduto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ImagemProdutoResponseDTO {
    private Long id;
    private Long produtoId;
    private String url;
    private Short ordem;
    private Boolean principal;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime criadoEm;

    public ImagemProdutoResponseDTO(Long id, Long produtoId, String url, Short ordem,
                                    Boolean principal, LocalDateTime criadoEm) {
        this.id = id;
        this.produtoId = produtoId;
        this.url = url;
        this.ordem = ordem;
        this.principal = principal;
        this.criadoEm = criadoEm;
    }
}