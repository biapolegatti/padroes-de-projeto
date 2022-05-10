package one.digitalinovation.gof.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO {

    private Long id;
    private String nome;

    private EnderecoDTO endereco;

}
