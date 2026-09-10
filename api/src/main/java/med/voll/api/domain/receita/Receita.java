package med.voll.api.domain.receita;


import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.medico.Medico;

import java.time.LocalDate;

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

    private String frequencia;

    @Column(name = "data_prescricao")
    private LocalDate dataPrescricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prontuario_id", nullable = false)
    private med.voll.api.domain.prontuario.Prontuario prontuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    private LocalDate data;

}
