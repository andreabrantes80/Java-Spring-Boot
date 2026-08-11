package med.voll.api.controller;

import med.voll.api.infra.email.EmailService;
import med.voll.api.infra.security.ResetTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class ResetTokenController {

    private final ResetTokenService resetTokenService;

    private final EmailService emailService;

    public ResetTokenController(ResetTokenService resetTokenService, EmailService emailService) {
        this.resetTokenService = resetTokenService;
        this.emailService = emailService;
    }

    // Endpoint para gerar token (esqueci senha)
    @PostMapping("/forgot")
    public ResponseEntity<String> forgot(@RequestParam String login) {
        String token = resetTokenService.gerarToken(login);

        String corpo = "Clique no link para redefinir sua senha: "
                + "http://localhost:4200/reset-senha?token=" + token;

        emailService.enviarEmail(login, "Redefinição de senha", corpo);
        // Aqui você pode enviar o token por email, mas por enquanto retorna direto
        return ResponseEntity.ok(token);
    }

    // Endpoint para redefinir senha
    @PostMapping("/reset")
    public ResponseEntity<Void> reset(@RequestParam String token, @RequestParam String novaSenha) {
        resetTokenService.redefinirSenha(token, novaSenha);
        return ResponseEntity.ok().build();
    }
}
