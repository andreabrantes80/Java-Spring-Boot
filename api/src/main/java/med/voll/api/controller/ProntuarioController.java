package med.voll.api.controller;


import med.voll.api.domain.prontuario.DadosAtualizacaoProntuario;
import med.voll.api.domain.prontuario.DadosCadastroProntuario;
import med.voll.api.domain.prontuario.DadosDetalhamentoProntuario;
import med.voll.api.domain.prontuario.Prontuario;
import med.voll.api.infra.prontuario.ProntuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prontuarios")
public class ProntuarioController {

    private final ProntuarioService prontuarioService;

    public ProntuarioController(ProntuarioService prontuarioService) {
        this.prontuarioService = prontuarioService;
    }

    @PostMapping
    public DadosDetalhamentoProntuario cadastrar(@RequestBody DadosCadastroProntuario dados) {
        var prontuario = prontuarioService.cadastrar(
                dados.pacienteId(),
                dados.consultaId(),
                dados.anotacoes()
        );
        return new DadosDetalhamentoProntuario(prontuario);
    }

    @GetMapping("/paciente/{id}")
    public List<DadosDetalhamentoProntuario> listarPorPaciente(@PathVariable Long id) {
        return prontuarioService.listarPorPaciente(id)
                .stream()
                .map(DadosDetalhamentoProntuario::new)
                .toList();
    }

    @PutMapping("/{id}")
    public DadosDetalhamentoProntuario atualizar(@PathVariable Long id,
                                                 @RequestBody DadosAtualizacaoProntuario dados) {
        var prontuario = prontuarioService.atualizar(id, dados);
        return new DadosDetalhamentoProntuario(prontuario);
    }
}
