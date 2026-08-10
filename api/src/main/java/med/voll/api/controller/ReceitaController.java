package med.voll.api.controller;

import med.voll.api.domain.receita.DadosCadastroReceita;
import med.voll.api.domain.receita.DadosDetalhamentoReceita;
import med.voll.api.infra.receita.PdfGenerator;
import med.voll.api.infra.receita.ReceitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @PostMapping
    public DadosDetalhamentoReceita cadastrar(@RequestBody
    DadosCadastroReceita dados) {
        var receita = receitaService.cadatrar(dados);
        return new DadosDetalhamentoReceita(receita);

    }

    @GetMapping("/prontuario/{id}")
    public List<DadosDetalhamentoReceita> listarPorProntuario(@PathVariable Long id){
        return receitaService.listarPorProntuario(id).stream().map(DadosDetalhamentoReceita::new).toList();
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> imprimir(@PathVariable Long id) {
        var receita = receitaService.buscarPorId(id);
        // Paciente
        var paciente = receita.getProntuario().getPaciente();
        String nomePaciente = paciente != null ? paciente.getNome() : "Não informado";
        String emailPaciente = paciente != null ? paciente.getEmail() : "Não informado";
        String telefonePaciente = paciente != null ? paciente.getTelefone() : "Não informado";

        // Médico
        String nomeMedico = receita.getProntuario().getConsulta() != null
                ? receita.getProntuario().getConsulta().getMedico().getNome()
                : "________________________"; // espaço para assinatura


        // Clínica
        String nomeClinica = "Clínica VollMed";
        String enderecoClinica = "Av. Principal, 123 - Brasília/DF";
        String telefoneClinica = "(61) 99999-9999";
        String logoPath = "src/main/resources/static/logo.png";

        byte[] pdf = PdfGenerator.gerar(receita, nomePaciente, emailPaciente, telefonePaciente, nomeMedico, nomeClinica, enderecoClinica, telefoneClinica, logoPath);

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "inline; filename=receita.pdf")
                .body(pdf);
    }

    @PutMapping("/{id}")
    public DadosDetalhamentoReceita atualizar(@PathVariable Long id,
                                              @RequestBody DadosCadastroReceita dados) {
        var receita = receitaService.atualizar(id, dados);
        return new DadosDetalhamentoReceita(receita);
    }
}
