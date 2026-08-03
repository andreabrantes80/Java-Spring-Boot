package med.voll.api.infra.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {


    public void enviarEmail(String destinatarioIgnorado, String assunto, String corpo) {
        Email from = new Email("no-reply@seuservico.com"); // remetente padrão
        Email to = new Email("meloabrantes@gmail.com");
        Content content = new Content("text/plain", corpo);
        Mail mail = new Mail(from, assunto, to, content);
        //Quando quiser voltar a enviar para os pacientes, basta trocar a linha:
        //Email to = new Email(destinatario);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao enviar e-mail", ex);
        }
    }
}
