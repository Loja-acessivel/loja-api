package br.com.apiloja.Service;


import br.com.apiloja.Dto.Vendedor.VendedorRequestDTO;
import br.com.apiloja.Dto.Vendedor.VendedorResponseDTO;
import br.com.apiloja.Mapper.VendedorMapper;
import br.com.apiloja.Model.Vendedor;
import br.com.apiloja.Repository.VendedorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VendedorService {
    private final VendedorRepository repo;
    private final VendedorMapper mapper;

    public VendedorResponseDTO inserir(VendedorRequestDTO dto){
        Vendedor vend = mapper.toEntity(dto);
        repo.save(vend);
        return mapper.toResponse(vend);
    }

    public List<VendedorResponseDTO> buscarTodos(){
        List<Vendedor> vendedor = repo.findAll();
        return mapper.toResponseList(vendedor);
    }

    public VendedorResponseDTO buscarPorId(Long id){
        Vendedor vendedor = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado com o ID: " + id));;
        return mapper.toResponse(vendedor);
    }


    public void deletar(Long id){
        Vendedor vendedor = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(""));
        repo.delete(vendedor);
    }

    public VendedorResponseDTO atualizar(Long id, VendedorRequestDTO dto) {
        Vendedor vendedor = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado com o ID: " + id));

        vendedor.setNome(dto.getNome());
        vendedor.setEmail(dto.getEmail());
        vendedor.setSenha(dto.getSenha());
        vendedor.setCpfCnpj(dto.getCpfCnpj());
        vendedor.setTelefone(dto.getTelefone());
        vendedor.setAvaliacao(dto.getAvaliacao());
        repo.save(vendedor);
        return mapper.toResponse(vendedor);
    }
}
