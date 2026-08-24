package med.voll.api.domain.exame;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.api.domain.prontuario.Prontuario;

import java.time.LocalDate;

@Entity
@Table(name = "exames")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // Hemograma, glicemia, etc.
    private String resultado;
    private LocalDate data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prontuario_id")
    private Prontuario prontuario;


    public Exame(DadosCadastroExame dados, Prontuario prontuario) {
        this.tipo = dados.tipo();
        this.resultado = dados.resultado();
        this.data = dados.data();
        this.prontuario = prontuario;
    }
}
