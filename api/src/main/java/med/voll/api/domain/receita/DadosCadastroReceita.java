package med.voll.api.domain.receita;

import jakarta.validation.constraints.NotNull;

public record DadosCadastroReceita(
        @NotNull Long prontuarioId,
        @NotNull Long medicoId,
        @NotNull String medicamento,
        @NotNull String dosagem,
        @NotNull String instrucoes,
        String frequencia
) {
}
