package one.digitalinovation.gof.service;

import one.digitalinovation.gof.model.dto.ClienteDTO;
import one.digitalinovation.gof.model.entity.Cliente;
import java.util.List;

public interface ClienteService {

    List<ClienteDTO> buscarTodos();

    ClienteDTO buscarPorId(Long id);

    ClienteDTO inserir(ClienteDTO clienteDTO);

    void atualizar(Long id, ClienteDTO cliente);

    void deletar(Long id);

}
