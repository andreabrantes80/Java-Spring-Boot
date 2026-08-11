package med.voll.api.controller;

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

    public ResetTokenController(ResetTokenService resetTokenService) {
        this.resetTokenService = resetTokenService;
    }

    // Endpoint para gerar token (esqueci senha)
    @PostMapping("/forgot")
    public ResponseEntity<String> forgot(@RequestParam String email) {
        String token = resetTokenService.gerarToken(email);
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
