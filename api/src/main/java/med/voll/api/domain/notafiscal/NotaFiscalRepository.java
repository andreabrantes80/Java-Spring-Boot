package med.voll.api.domain.notafiscal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {
    List<NotaFiscal> findByProntuarioId(Long prontuarioId);
}
