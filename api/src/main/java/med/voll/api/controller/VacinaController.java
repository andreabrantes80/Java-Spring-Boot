package med.voll.api.controller;

import med.voll.api.domain.prontuario.ProntuarioRepository;
import med.voll.api.domain.vacina.DadosCadastroVacina;
import med.voll.api.domain.vacina.DadosDetalhamentoVacina;
import med.voll.api.domain.vacina.Vacina;
import med.voll.api.domain.vacina.VacinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacinas")
public class VacinaController {
    @Autowired
    private VacinaRepository repository;

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody DadosCadastroVacina dados) {
        var prontuario = prontuarioRepository.findById(dados.prontuarioId())
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));

        var vacina = repository.save(new Vacina(dados, prontuario));
        return ResponseEntity.ok(new DadosDetalhamentoVacina(vacina));
    }

    @GetMapping("/prontuario/{id}")
    public ResponseEntity<List<DadosDetalhamentoVacina>> listarPorProntuario(@PathVariable Long id) {
        var vacinas = repository.findByProntuario_Id(id).stream()
                .map(DadosDetalhamentoVacina::new).toList();
        return ResponseEntity.ok(vacinas);
    }
}
