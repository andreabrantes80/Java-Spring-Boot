package med.voll.api.infra.senha;

import med.voll.api.dto.SenhaDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
@Service
public class SenhaService {
    private final SimpMessagingTemplate messagingTemplate;

    public SenhaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public String gerarSenha() {
        // lógica para gerar senha
        return "A001";
    }

    public String chamarSenha(String nomeMedico) {
        String senha = gerarSenha();
        messagingTemplate.convertAndSend("/topic/chamadas", new SenhaDTO(senha, nomeMedico));
        return senha;
    }
}
