package med.voll.api.domain.prontuario;


import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Entity(name = "Prontuario")
@Table(name = "prontuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // cada prontuário pertence a um paciente
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY) // opcional: vincular ao atendimento/consulta
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;

    private String anotacoes; // texto livre do médico
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
