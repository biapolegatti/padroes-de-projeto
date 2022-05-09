package service.impl;

import model.Cliente;
import model.ClienteRepostiory;
import model.Endereco;
import model.EnderecoRepository;
import org.bouncycastle.est.EnrollmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ClienteService;
import service.ViaCepService;

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
    public Iterable<Cliente> buscarTodos() {
        return clienteRepostiory.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepostiory.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteCep(cliente);
    }

    private void salvarClienteCep(Cliente cliente){
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return null;
        });
        cliente.setEndereco(endereco);
        clienteRepostiory.save(cliente);
    }
    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepostiory.findById(id);
        if (clienteBd.isPresent()) {
            salvarClienteCep(cliente);

       }

    }

    @Override
    public void deletar(Long id) {
        clienteRepostiory.deleteById(id);

    }
}
