package med.voll.api.domain.receita;

public record DadosCadastroReceita(
        Long prontuarioId,
        String medicamento,
        String dosagem,
        String instrucoes
) {
}
