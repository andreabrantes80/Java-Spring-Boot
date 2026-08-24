package med.voll.api.domain.vacina;

import java.time.LocalDate;

public record DadosDetalhamentoVacina(
        Long id,
        String nome,
        LocalDate dataAplicacao,
        String lote,
        Long prontuarioId
) {
    public DadosDetalhamentoVacina(Vacina vacina) {
        this(vacina.getId(), vacina.getNome(), vacina.getDataAplicacao(), vacina.getLote(),
                vacina.getProntuario() != null ? vacina.getProntuario().getId() : null);
    }
}
