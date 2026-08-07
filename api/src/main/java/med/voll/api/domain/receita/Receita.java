package med.voll.api.domain.receita;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "receitas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicamento;
    private String dosagem;
    private String instrucoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id", nullable = false)
    private med.voll.api.domain.prontuario.Prontuario prontuario;

}
