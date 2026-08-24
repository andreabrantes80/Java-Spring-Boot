package med.voll.api.infra.prontuario;

import jakarta.transaction.Transactional;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.prontuario.DadosAtualizacaoProntuario;
import med.voll.api.domain.prontuario.Prontuario;
import med.voll.api.domain.prontuario.ProntuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProntuarioService {

    private final ProntuarioRepository prontuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;

    public ProntuarioService(ProntuarioRepository prontuarioRepository,
                             PacienteRepository pacienteRepository,
                             ConsultaRepository consultaRepository) {
        this.prontuarioRepository = prontuarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
    }

    @Transactional
    public Prontuario cadastrar(Long pacienteId, Long consultaId, String anotacoes) {
        var paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Consulta consulta = null;
        if (consultaId != null) {
            consulta = consultaRepository.findById(consultaId)
                    .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        }

        var prontuario = new Prontuario(paciente, consulta, anotacoes);
        return prontuarioRepository.save(prontuario);
    }

    public List<Prontuario> listar() {
        return prontuarioRepository.findAll();
    }


    public List<Prontuario> listarPorPaciente(Long pacienteId) {
        return prontuarioRepository.findByPaciente_Id(pacienteId);
    }

    @Transactional
    public Prontuario atualizar(Long id, DadosAtualizacaoProntuario dados) {
        var prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));

        if (dados.consultaId() != null) {
            var consulta = consultaRepository.findById(dados.consultaId())
                    .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
            prontuario.setConsulta(consulta);
        }

        if (dados.anotacoes() != null && !dados.anotacoes().isBlank()) {
            prontuario.setAnotacoes(dados.anotacoes());
        }

        return prontuarioRepository.save(prontuario);
    }

    @Transactional
    public void deletar(Long id) {
        var prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));
        prontuarioRepository.delete(prontuario);
    }

}
