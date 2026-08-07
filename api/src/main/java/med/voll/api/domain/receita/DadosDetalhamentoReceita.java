package med.voll.api.domain.receita;

public record DadosDetalhamentoReceita(
        Long id,
        Long prontuarioId,
        String medicamento,
        String dosagem,
        String instrucoes
) {

    public DadosDetalhamentoReceita(Receita receita) {
        this(receita.getId(),
                receita.getProntuario().getId(),
                receita.getMedicamento(),
                receita.getDosagem(),
                receita.getInstrucoes());
    }
}
