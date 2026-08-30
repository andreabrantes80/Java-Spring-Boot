package med.voll.api.domain.notafiscal;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import med.voll.api.domain.prontuario.Prontuario;

import java.time.LocalDateTime;

@Entity
@Table(name = "notas_fiscais")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dados do prestador (médico ou clínica)
    private String prestadorNome;
    private String prestadorCpfCnpj;

    // Dados do paciente (tomador do serviço)
    private String pacienteNome;
    private String pacienteCpf;

    // Serviço prestado
    private String descricaoServico;
    private Double valorServico;

    // Dados fiscais
    private Double aliquotaIss;
    private Double valorImposto;

    private LocalDateTime dataEmissao;

    // Relacionamento opcional com prontuário ou consulta
    @ManyToOne
    @JoinColumn(name = "prontuario_id")
    private Prontuario prontuario;
}
