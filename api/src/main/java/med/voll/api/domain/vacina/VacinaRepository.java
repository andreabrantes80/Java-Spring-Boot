package med.voll.api.domain.vacina;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacinaRepository extends JpaRepository<Vacina, Long> {
    List<Vacina> findByProntuario_Id(Long prontuarioId);
}
