package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgendaDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EmailService emailService;


    //Uma forma de injetar todos os validadores numa list injetar a interface
    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;
    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    //Essa é uma classe Service executa as regras de negócio e validações da aplicação
    public DadosDetalhamentoConsulta Agendar(DadosAgendamentoConsulta dados) {

        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado nãO existe.");

        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado nãO existe.");
        }
        //aqui fazemos um foreach para passar em todos os validadores injetados
        validadores.forEach(v -> v.validar(dados));


        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());

        var medico = escolherMedico(dados);
        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível nessa data!");
        }

        var consulta = new Consulta(medico, paciente, dados.data());

        consultaRepository.save(consulta);


        //Formatador no padrão brasil
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataHoraFormatada = dados.data().format(formatter);

        String corpoHtml =
                "<html><body style='font-family: Arial, sans-serif;'>" +
                        "<h2 style='color:#2E86C1;'>Consulta Agendada</h2>" +
                        "<p>Olá <strong>" + paciente.getNome() + "</strong>,</p>" +
                        "<p>Sua consulta com o Dr(a). <strong>" + medico.getNome() + "</strong> foi confirmada.</p>" +
                        "<p><strong>Data:</strong> " + dados.data().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "</p>" +
                        "</body></html>";

        // Dispara e-mail de confirmação
        emailService.enviarEmail(
                paciente.getEmail(),
                "Consulta Agendada",
                corpoHtml
        );

        return new DadosDetalhamentoConsulta(consulta);


    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());

    }

    public void cancelar(DadosCancelamentoConsulta dados) {

        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informada não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());

        consulta.cancelar(dados.motivo());


        //Formatador no padrão brasil
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataHoraFormatada = consulta.getData().format(formatter);

        String corpoHtml =
                "<html><body style='font-family: Arial, sans-serif;'>" +
                        "<h2 style='color:#C0392B;'>Consulta Cancelada</h2>" +
                        "<p>Olá <strong>" + consulta.getPaciente().getNome() + "</strong>,</p>" +
                        "<p>Sua consulta com o Dr(a). <strong>" + consulta.getMedico().getNome() + "</strong> " +
                        "marcada para <strong>" + dataHoraFormatada + "</strong> foi cancelada.</p>" +
                        "<p><strong>Motivo:</strong> " + dados.motivo() + "</p>" +
                        "<hr>" +
                        "<p style='font-size:12px; color:#888;'>Clinica VillMed - Brasília</p>" +
                        "</body></html>";

        // Dispara e-mail de cancelamento
        emailService.enviarEmail(
                consulta.getPaciente().getEmail(),
                "Consulta Cancelada",
                corpoHtml
        );

    }

    public List<DadosDisponibilidadeConsulta> disponibilidade(LocalDate data) {

        var inicio = data.atTime(7, 0);

        var fim = data.atTime(18, 0);

        var consultas = consultaRepository.findAllByDataBetween(inicio, fim);

        List<DadosDisponibilidadeConsulta> retorno = new ArrayList<>();

        for (int hora = 7; hora <= 18; hora++) {

            LocalTime horario = LocalTime.of(hora, 0);

            boolean ocupado = consultas.stream()

                    .anyMatch(c ->
                            c.getData().toLocalTime().equals(horario));

            retorno.add(
                    new DadosDisponibilidadeConsulta(
                            horario,
                            !ocupado
                    )
            );
        }

        return retorno;

    }
}
