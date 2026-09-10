package med.voll.api.infra.receita;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import med.voll.api.domain.receita.Receita;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PdfGenerator {

    public static byte[] gerar(Receita receita, String nomePaciente, String emailPaciente, String telefonePaciente,
                               String nomeMedico, String nomeClinica, String enderecoClinica, String telefoneClinica, String logoPath) {
        try {
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            // adiciona o evento de rodapé
            writer.setPageEvent(new FooterEvent(nomeClinica + " - " + enderecoClinica + " | Tel: " + telefoneClinica));
            document.open();

            // Logo da clínica
            URL logoUrl = PdfGenerator.class.getResource("/static/logo.png");
            if (logoUrl != null) {
                Image logo = Image.getInstance(logoUrl);
                logo.scaleToFit(100, 100);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
                document.add(new Paragraph("\n"));
            } else {
                System.out.println("Logo não encontrada em: " + logoPath);
            }


            // Cabeçalho
            Paragraph header = new Paragraph(nomeClinica + "\nTelefone: " + telefoneClinica + " | Email: contato@" + nomeClinica.toLowerCase() + ".com");
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph("\n"));

            // Dados do paciente
            document.add(new Paragraph("Paciente: " + nomePaciente));
            document.add(new Paragraph("Email: " + emailPaciente));
            document.add(new Paragraph("Telefone: " + telefonePaciente));
            document.add(new Paragraph("\n"));

            // Receita
            document.add(new Paragraph("Medicamento: " + receita.getMedicamento()));
            document.add(new Paragraph("Dosagem: " + receita.getDosagem()));
            document.add(new Paragraph("Instruções: " + receita.getInstrucoes()));
            document.add(new Paragraph("\n\n"));

            // Assinatura centralizada
            Paragraph assinatura = new Paragraph("________________________________________");
            assinatura.setAlignment(Element.ALIGN_CENTER);
            document.add(assinatura);

            Paragraph medico = new Paragraph("Médico: " + nomeMedico);
            medico.setAlignment(Element.ALIGN_CENTER);
            document.add(medico);

            // Data da impressão
            String dataImpressao = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Paragraph data = new Paragraph("Data: " + dataImpressao);
            data.setAlignment(Element.ALIGN_CENTER);
            document.add(data);

            document.add(new Paragraph("\n\n"));

            // Rodapé
//            Paragraph footer = new Paragraph(nomeClinica + " - " + enderecoClinica + " | Tel: " + telefoneClinica);
//            footer.setAlignment(Element.ALIGN_CENTER);
//            document.add(footer);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }
}

