package med.voll.api.domain.vacina;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.api.domain.prontuario.Prontuario;

import java.time.LocalDate;

@Entity
@Table(name = "vacinas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate dataAplicacao;
    private String lote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prontuario_id")
    private Prontuario prontuario;

    public Vacina(DadosCadastroVacina dados, Prontuario prontuario) {
        this.nome = dados.nome();
        this.dataAplicacao = dados.dataAplicacao();
        this.lote = dados.lote();
        this.prontuario = prontuario;
    }
}
