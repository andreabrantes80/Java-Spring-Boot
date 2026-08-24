package med.voll.api.controller;

import med.voll.api.domain.exame.DadosCadastroExame;
import med.voll.api.domain.exame.DadosDetalhamentoExame;
import med.voll.api.domain.exame.Exame;
import med.voll.api.domain.exame.ExameRepository;
import med.voll.api.domain.prontuario.ProntuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exames")
public class ExameController {

    @Autowired
    private ExameRepository repository;

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody DadosCadastroExame dados) {
        var prontuario = prontuarioRepository.findById(dados.prontuarioId())
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));

        var exame = repository.save(new Exame(dados, prontuario));
        return ResponseEntity.ok(new DadosDetalhamentoExame(exame));
    }

    @GetMapping("/prontuario/{id}")
    public ResponseEntity<List<DadosDetalhamentoExame>> listarPorProntuario(@PathVariable Long id) {
        var exames = repository.findByProntuario_Id(id).stream()
                .map(DadosDetalhamentoExame::new).toList();
        return ResponseEntity.ok(exames);
    }
}
