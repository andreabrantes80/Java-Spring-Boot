package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultaRepository consultaRepository;
    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = agenda.Agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoConsulta>> listar(
            @PageableDefault(size = 10, sort = "data") Pageable pageable) {

        var page = consultaRepository
                .findAll(pageable)
                .map(DadosDetalhamentoConsulta::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoConsulta> detalhar(
            @PathVariable Long id) {

        var consulta = consultaRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
    }

    @PutMapping("/{id}/reagendar")
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> reagendar(
            @PathVariable Long id,
            @RequestBody @Valid DadosReagendamentoConsulta dados) {

        var consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        consulta.setData(dados.novaData());
        consulta.setMotivoReagendamento(dados.motivo()); // se quiser registrar motivo
        consultaRepository.save(consulta);

        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
    }


    @GetMapping("/disponibilidade")
    public ResponseEntity<List<DadosDisponibilidadeConsulta>> disponibilidade(
            @RequestParam LocalDate data){

        return ResponseEntity.ok(
                agenda.disponibilidade(data)
        );

    }
}
