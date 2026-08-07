package med.voll.api.domain.receita;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByProntuario_Id(Long prontuarioId);
}

