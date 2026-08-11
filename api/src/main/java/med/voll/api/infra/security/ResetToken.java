package med.voll.api.infra.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import med.voll.api.domain.usuario.Usuario;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private LocalDateTime expiryDate;

    @ManyToOne
    private Usuario usuario;

    // getters e setters
}
