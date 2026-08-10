package med.voll.api.infra.receita;

import med.voll.api.domain.prontuario.ProntuarioRepository;
import med.voll.api.domain.receita.DadosCadastroReceita;
import med.voll.api.domain.receita.Receita;
import med.voll.api.domain.receita.ReceitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final ProntuarioRepository prontuarioRepository;

    public ReceitaService(ReceitaRepository receitaRepository, ProntuarioRepository prontuarioRepository) {
        this.receitaRepository = receitaRepository;
        this.prontuarioRepository = prontuarioRepository;
    }

    @Transactional
    public Receita cadatrar(DadosCadastroReceita dados) {
        var prontuario = prontuarioRepository.findById(dados.prontuarioId()).orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));

        var receita = new Receita(null, dados.medicamento(), dados.dosagem(), dados.instrucoes(), prontuario);
        return receitaRepository.save(receita);
    }

    public List<Receita> listarPorProntuario(Long prontuarioId) {
        return receitaRepository.findByProntuario_Id(prontuarioId);
    }

    public Receita buscarPorId(Long id) {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
    }

    @Transactional
    public Receita atualizar(Long id, DadosCadastroReceita dados) {
        var receita = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));

        receita.setMedicamento(dados.medicamento());
        receita.setDosagem(dados.dosagem());
        receita.setInstrucoes(dados.instrucoes());

        return receitaRepository.save(receita);
    }



}
