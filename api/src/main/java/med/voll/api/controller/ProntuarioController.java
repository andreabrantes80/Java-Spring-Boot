package med.voll.api.controller;


import med.voll.api.domain.prontuario.DadosCadastroProntuario;
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
    public Prontuario cadastrar(@RequestBody DadosCadastroProntuario dados) {
        return prontuarioService.cadastrar(dados.pacienteId(), dados.consultaId(), dados.anotacoes());
    }

    @GetMapping("/paciente/{id}")
    public List<Prontuario> listarPorPaciente(@PathVariable Long id) {
        return prontuarioService.listarPorPaciente(id);
    }
}
