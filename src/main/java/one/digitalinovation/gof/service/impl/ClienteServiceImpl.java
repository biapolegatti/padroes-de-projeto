package one.digitalinovation.gof.service.impl;

import one.digitalinovation.gof.exception.NotFoundException;
import one.digitalinovation.gof.model.dto.ClienteDTO;
import one.digitalinovation.gof.model.dto.EnderecoDTO;
import one.digitalinovation.gof.model.entity.Cliente;
import one.digitalinovation.gof.model.entity.Endereco;
import one.digitalinovation.gof.repository.ClienteRepostiory;
import one.digitalinovation.gof.repository.EnderecoRepository;
import one.digitalinovation.gof.service.ClienteService;
import one.digitalinovation.gof.service.ViaCepService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepostiory clienteRepostiory;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    @Override
    public List<ClienteDTO> buscarTodos() {
        Iterable<Cliente> clientes = clienteRepostiory.findAll();
        List<ClienteDTO> listaClientesDTO = new ArrayList<>();

        for (Cliente cliente : clientes) {
            listaClientesDTO.add(this.convertClienteToDTO(cliente));
        }
        return listaClientesDTO;
    }

    @Override
    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepostiory.findById(id).orElseThrow(NotFoundException::new);
        return this.convertClienteToDTO(cliente);
    }

    @Override
    public ClienteDTO inserir(ClienteDTO clienteDTO) {
        Cliente cliente = this.convertDTOToCliente(clienteDTO);
        return salvarClienteCep(cliente);
    }

    private ClienteDTO salvarClienteCep(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            return enderecoRepository.save(novoEndereco);
        });

        cliente.setEndereco(endereco);
        cliente = clienteRepostiory.save(cliente);

        return convertClienteToDTO(cliente);
    }

    @Override
    public void atualizar(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = this.convertDTOToCliente(clienteDTO);
        Optional<Cliente> clienteBd = clienteRepostiory.findById(id);
        if (clienteBd.isPresent()) {
            salvarClienteCep(cliente);
        }
    }
    @Override
    public void deletar(Long id) {
        if (!clienteRepostiory.existsById(id)) {
            throw new NotFoundException("Cliente não encontrado");
        }
        clienteRepostiory.deleteById(id);
    }
    private ClienteDTO convertClienteToDTO(@NotNull Cliente cliente) {
        Endereco endereco = cliente.getEndereco();
        EnderecoDTO enderecoDTO = EnderecoDTO.builder()
                .cep(endereco.getCep())
                .cidade(endereco.getLocalidade())
                .uf(endereco.getUf())
                .bairro(endereco.getBairro())
                .logradouro(endereco.getLogradouro())
                .ibge(endereco.getIbge())
                .gia(endereco.getGia())
                .ddd(endereco.getDdd())
                .siafi(endereco.getSiafi())
                .build();
        return ClienteDTO.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .endereco(enderecoDTO)
                .build();
    }

    private Cliente convertDTOToCliente(ClienteDTO clienteDTO) {
        EnderecoDTO enderecoDTO = clienteDTO.getEndereco();
        Endereco endereco = Endereco.builder()
                .cep(enderecoDTO.getCep())
                .localidade(enderecoDTO.getCidade())
                .uf(enderecoDTO.getUf())
                .bairro(enderecoDTO.getBairro())
                .logradouro(enderecoDTO.getLogradouro())
                .ibge(enderecoDTO.getIbge())
                .gia(enderecoDTO.getGia())
                .ddd(enderecoDTO.getDdd())
                .siafi(enderecoDTO.getSiafi())
                .build();
        return Cliente.builder()
                .id(clienteDTO.getId())
                .nome(clienteDTO.getNome())
                .endereco(endereco)
                .build();
    }
}