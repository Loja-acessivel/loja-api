package br.com.apiloja.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UploadArquivoService {

    private final Cloudinary cloudinary;

    public UploadResultado salvarArquivo(MultipartFile arquivo) {
        validarImagem(arquivo);

        try {
            Map<?, ?> resultado = cloudinary.uploader().upload(
                    arquivo.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "loja/produtos",
                            "resource_type", "image",
                            "unique_filename", true
                    )
            );

            return new UploadResultado(
                    resultado.get("secure_url").toString(),
                    resultado.get("public_id").toString()
            );
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao enviar a imagem para o Cloudinary", e);
        }
    }

    public void excluirArquivo(String publicId) {
        if (publicId == null || publicId.isBlank()) {
            return;
        }

        try {
            cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap(
                            "resource_type", "image",
                            "invalidate", true
                    )
            );
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao excluir a imagem do Cloudinary", e);
        }
    }

    private void validarImagem(MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("A imagem é obrigatória");
        }

        String contentType = arquivo.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("O arquivo enviado deve ser uma imagem");
        }
    }
}
