package one.digitalinovation.gof.repository;

import one.digitalinovation.gof.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepostiory extends CrudRepository <Cliente, Long> {
}
