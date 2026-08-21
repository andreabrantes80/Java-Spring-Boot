package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosCadastroUsuario;
import med.voll.api.domain.usuario.Role;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {

        if (repository.findByLogin(dados.login()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe.");
        }

        var senhaCriptografada = encoder.encode(dados.senha());

        repository.save(new Usuario(dados, senhaCriptografada));

        return ResponseEntity.ok("Usuário cadastrado com sucesso.");
    }

    @PostMapping("/usuarios/admin")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity cadastrarAdmin(@RequestBody @Valid DadosCadastroUsuario dados) {
        if (repository.findByLogin(dados.login()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe.");
        }
        var senhaCriptografada = encoder.encode(dados.senha());
        repository.save(new Usuario(new DadosCadastroUsuario(dados.login(), dados.senha(), Role.ADMIN), senhaCriptografada));
        return ResponseEntity.ok("Administrador cadastrado com sucesso.");
    }

}
