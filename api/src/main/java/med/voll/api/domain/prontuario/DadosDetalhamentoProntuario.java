package med.voll.api.domain.prontuario;

import java.time.LocalDateTime;

public record DadosDetalhamentoProntuario(Long id,
                                          Long pacienteId,
                                          Long consultaId,
                                          String anotacoes,
                                          LocalDateTime dataCriacao) {

    public DadosDetalhamentoProntuario(Prontuario prontuario) {
        this(prontuario.getId(),
                prontuario.getPaciente().getId(),
                prontuario.getConsulta() != null ? prontuario.getConsulta().getId() : null,
                prontuario.getAnotacoes(),
                prontuario.getDataCriacao());
    }
}
