package med.voll.api.domain.vacina;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCadastroVacina(
        @NotBlank String nome,
        @NotNull LocalDate dataAplicacao,
        String lote,
        @NotNull Long prontuarioId
) {
}
