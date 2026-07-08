package br.com.apiloja.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadArquivoService {

    // Define uma pasta no seu computador para guardar as imagens de teste
    private final String DIRETORIO_IMAGENS = "C:/Users/lucascosta-ieg/OneDrive - Instituto J&F/Área de Trabalho/loja-api";

    public String salvarArquivo(MultipartFile arquivo) {
        try {
            // Cria a pasta caso ela não exista
            File pasta = new File(DIRETORIO_IMAGENS);
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            // Gera um nome único para o arquivo não sobrescrever outro com o mesmo nome
            String nomeOriginal = arquivo.getOriginalFilename();
            String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            String nomeUnico = UUID.randomUUID().toString() + extensao;

            // Salva o arquivo na pasta
            Path caminhoCompleto = Paths.get(DIRETORIO_IMAGENS + nomeUnico);
            Files.write(caminhoCompleto, arquivo.getBytes());

            // Retorna o "link" simula o link que iria para o banco
            return "http://localhost:8080/imagens/" + nomeUnico;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a imagem do produto", e);
        }
    }
}