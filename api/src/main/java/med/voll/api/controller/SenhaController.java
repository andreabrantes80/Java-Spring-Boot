package med.voll.api.controller;

import med.voll.api.dto.SenhaDTO;
import med.voll.api.infra.senha.SenhaService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/senhas")
public class SenhaController {

    private final SenhaService senhaService;

    public SenhaController(SenhaService senhaService) {
        this.senhaService = senhaService;
    }

    @PostMapping("/gerar")
    public String gerarSenha() {
        return senhaService.gerarSenha();
    }

    @PostMapping("/chamar")
    public String chamarSenha(@RequestParam String nomeMedico) {
        return senhaService.chamarSenha(nomeMedico);
    }
}

