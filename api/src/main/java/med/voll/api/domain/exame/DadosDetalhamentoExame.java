package med.voll.api.domain.exame;

import java.time.LocalDate;

public record DadosDetalhamentoExame(
        Long id,
        String tipo,
        String resultado,
        LocalDate data,
        Long prontuarioId
) {
    public DadosDetalhamentoExame(Exame exame) {
        this(exame.getId(), exame.getTipo(), exame.getResultado(), exame.getData(),
                exame.getProntuario() != null ? exame.getProntuario().getId() : null);
    }
}
