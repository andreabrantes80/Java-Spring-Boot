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
        var receita = receitaService.buscarPorId(id); // exemplo simples
        String conteudo = "Receita Médica\n\nMedicamento: " + receita.getMedicamento() +
                "\nDosagem: " + receita.getDosagem() +
                "\nInstruções: " + receita.getInstrucoes();

        byte[] pdf = PdfGenerator.gerar(conteudo);

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "inline; filename=receita.pdf")
                .body(pdf);
    }
}
