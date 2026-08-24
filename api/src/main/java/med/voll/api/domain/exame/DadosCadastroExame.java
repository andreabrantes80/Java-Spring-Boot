package med.voll.api.domain.exame;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCadastroExame(
        @NotBlank String tipo,
        @NotBlank String resultado,
        @NotNull LocalDate data,
        @NotNull Long prontuarioId
) {
}
