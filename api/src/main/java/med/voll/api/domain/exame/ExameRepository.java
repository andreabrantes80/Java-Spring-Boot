package med.voll.api.domain.exame;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExameRepository extends JpaRepository<Exame, Long> {
    List<Exame> findByProntuario_Id(Long prontuarioId);
}
