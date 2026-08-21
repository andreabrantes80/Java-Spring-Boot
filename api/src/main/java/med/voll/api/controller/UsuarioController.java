package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.usuario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/admin")
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

    @PutMapping("/{id}/senha")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity alterarSenha(@PathVariable Long id, @RequestBody DadosAlterarSenha dados) {
        var usuario = repository.findById(id).orElseThrow();
        // opcional: validar senhaAtual
        if (!encoder.matches(dados.senhaAtual(), usuario.getSenha())) {
            return ResponseEntity.badRequest().body("Senha atual incorreta.");
        }

        usuario.setSenha(encoder.encode(dados.novaSenha()));
        return ResponseEntity.ok("Senha alterada com sucesso.");
    }

    @PutMapping("/{id}/nome")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity alterarNome(@PathVariable Long id, @RequestBody String novoNome) {
        var usuario = repository.findById(id).orElseThrow();
        usuario.setLogin(novoNome); // ou outro campo se tiver "nome" separado
        return ResponseEntity.ok("Nome alterado com sucesso.");
    }


}
