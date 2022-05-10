package one.digitalinovation.gof.controller;

import one.digitalinovation.gof.model.dto.ClienteDTO;
import one.digitalinovation.gof.model.entity.Cliente;
import one.digitalinovation.gof.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteDTO> buscarTodos() {
        return clienteService.buscarTodos();

    }

    @GetMapping("/{id}")
    public ClienteDTO buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO inserir(@RequestBody ClienteDTO clienteDTO) {
        return clienteService.inserir(clienteDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Long id, @RequestBody ClienteDTO cliente) {
        clienteService.atualizar(id, cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        clienteService.deletar(id);
    }
}