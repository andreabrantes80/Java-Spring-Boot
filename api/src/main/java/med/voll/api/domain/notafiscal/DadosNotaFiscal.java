package med.voll.api.domain.notafiscal;

import java.time.LocalDateTime;

public record DadosNotaFiscal(
        Long id,
        String prestadorNome,
        String prestadorCpfCnpj,
        String pacienteNome,
        String pacienteCpf,
        String descricaoServico,
        Double valorServico,
        Double aliquotaIss,
        Double valorImposto,
        LocalDateTime dataEmissao,
        Long prontuarioId
) {
    public DadosNotaFiscal(NotaFiscal nf) {
        this(
                nf.getId(),
                nf.getPrestadorNome(),
                nf.getPrestadorCpfCnpj(),
                nf.getPacienteNome(),
                nf.getPacienteCpf(),
                nf.getDescricaoServico(),
                nf.getValorServico(),
                nf.getAliquotaIss(),
                nf.getValorImposto(),
                nf.getDataEmissao(),
                nf.getProntuario() != null ? nf.getProntuario().getId() : null
        );
    }
}
