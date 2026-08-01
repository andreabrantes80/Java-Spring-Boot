package med.voll.api.domain.consulta;

import java.time.LocalTime;

public record DadosDisponibilidadeConsulta(
        LocalTime hora,
        Boolean livre
) {
}
