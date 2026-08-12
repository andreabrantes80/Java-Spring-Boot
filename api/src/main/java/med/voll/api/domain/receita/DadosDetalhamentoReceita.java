package med.voll.api.domain.receita;

import java.time.LocalDate;

public record DadosDetalhamentoReceita(
        Long id,
        Long prontuarioId,
        String medicamento,
        String dosagem,
        String instrucoes,
        LocalDate data
) {

    public DadosDetalhamentoReceita(Receita receita) {
        this(receita.getId(),
                receita.getProntuario().getId(),
                receita.getMedicamento(),
                receita.getDosagem(),
                receita.getInstrucoes(),
                receita.getData());
    }
}
