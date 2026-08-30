package med.voll.api.controller;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.notafiscal.NotaFiscal;
import med.voll.api.domain.notafiscal.NotaFiscalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notas")
@RequiredArgsConstructor
public class NotaFiscalController {

    private final NotaFiscalRepository repository;

    // Emitir nota fiscal
    @PostMapping
    public ResponseEntity<NotaFiscal> emitir(@RequestBody NotaFiscal notaFiscal) {
        notaFiscal.setDataEmissao(java.time.LocalDateTime.now());
        NotaFiscal salva = repository.save(notaFiscal);
        return ResponseEntity.ok(salva);
    }

    // Consultar nota por ID
    @GetMapping("/{id}")
    public ResponseEntity<NotaFiscal> consultar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Listar notas de um prontuário
    @GetMapping("/prontuario/{prontuarioId}")
    public ResponseEntity<List<NotaFiscal>> listarPorProntuario(@PathVariable Long prontuarioId) {
        List<NotaFiscal> notas = repository.findByProntuarioId(prontuarioId);
        return ResponseEntity.ok(notas);
    }
}
