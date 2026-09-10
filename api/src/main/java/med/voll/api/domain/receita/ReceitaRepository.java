package med.voll.api.domain.receita;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByProntuario_Id(Long prontuarioId);

    @Query("SELECT r FROM Receita r " +
            "JOIN FETCH r.prontuario p " +
            "LEFT JOIN FETCH p.consulta c " +
            "LEFT JOIN FETCH c.medico " +
            "WHERE r.id = :id")
    Optional <Receita> buscarPorIdComMedico(@Param("id") Long id);

}

