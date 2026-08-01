package med.voll.api.domain.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
    boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

    Page<Consulta> findByCanceladaFalse(Pageable pageable);

    List<Consulta> findAllByDataBetween(
            LocalDateTime inicio,
            LocalDateTime fim
    );
}
