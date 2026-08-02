package med.voll.api.infra.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmail(String destinatario, String assunto, String corpo) {
        // Implementação do envio de e-mail usando mailSender

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);
        mailSender.send(mensagem);
    }
}
