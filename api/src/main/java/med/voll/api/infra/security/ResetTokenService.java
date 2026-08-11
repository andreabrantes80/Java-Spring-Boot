package med.voll.api.infra.security;

import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ResetTokenService {

    private final ResetTokenRepository tokenRepo;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder encoder;

    public ResetTokenService(ResetTokenRepository tokenRepo,
                             UsuarioRepository usuarioRepo,
                             PasswordEncoder encoder) {
        this.tokenRepo = tokenRepo;
        this.usuarioRepo = usuarioRepo;
        this.encoder = encoder;
    }

    public String gerarToken(String login) {
        Usuario usuario =  usuarioRepo.findByLogin(login);
        if (usuario == null) throw new RuntimeException("Usuário não encontrado");

        String token = UUID.randomUUID().toString();
        ResetToken rt = new ResetToken();
        rt.setToken(token);
        rt.setUsuario(usuario);
        rt.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        tokenRepo.save(rt);

        return token;
    }

    public void redefinirSenha(String token, String novaSenha) {
        ResetToken rt = tokenRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (rt.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }

        Usuario usuario = rt.getUsuario();
        usuario.setSenha(encoder.encode(novaSenha));
        usuarioRepo.save(usuario);

        tokenRepo.delete(rt);
    }
}
