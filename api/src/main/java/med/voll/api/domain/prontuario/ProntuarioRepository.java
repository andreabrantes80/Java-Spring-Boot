package med.voll.api.domain.prontuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    List<Prontuario> findByPacienteId(Long pacienteId);
}
