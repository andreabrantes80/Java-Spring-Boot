package med.voll.api.domain.prontuario;

public record DadosCadastroProntuario(
        Long pacienteId,
        Long consultaId,
        String anotacoes
) {
}
