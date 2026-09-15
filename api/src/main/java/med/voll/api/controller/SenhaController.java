package med.voll.api.controller;


import med.voll.api.infra.senha.SenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/senhas")
public class SenhaController {

    @Autowired
    private SenhaService senhaService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/gerar")
    public String gerarSenha() {
        return senhaService.gerarSenha();
    }

    @PostMapping("/chamar")
    public String chamarSenha(@RequestParam String nomeMedico) {
        String senha = senhaService.chamarProximaSenha();
        if (senha != null) {
            // envia para todos conectados na TV
            messagingTemplate.convertAndSend("/topic/chamadas",
                    new ChamadaDTO(nomeMedico, senha));
        }
        return senha;
    }
}

class ChamadaDTO {
    private String medico;
    private String senha;

    public ChamadaDTO(String medico, String senha) {
        this.medico = medico;
        this.senha = senha;
    }
    public String getMedico() { return medico; }
    public String getSenha() { return senha; }
}
