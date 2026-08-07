package med.voll.api.infra.receita;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import med.voll.api.domain.receita.Receita;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static byte[] gerar(Receita receita, String nomePaciente, String emailPaciente, String telefonePaciente,
                                      String nomeMedico, String nomeClinica, String enderecoClinica, String telefoneClinica, String logoPath) {
        try {
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);
            document.open();

            // Logo da clínica
//            if (logoPath != null) {
//                Image logo = Image.getInstance(logoPath);
//                logo.scaleToFit(100, 100);
//                logo.setAlignment(Element.ALIGN_CENTER);
//                document.add(logo);
//            }

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
            document.add(new Paragraph("\n"));

            // Médico + linha para assinatura
            document.add(new Paragraph("Médico: " + nomeMedico));
            document.add(new Paragraph("________________________________________"));
            document.add(new Paragraph("Assinatura\n\n"));

            // Rodapé
            Paragraph footer = new Paragraph(nomeClinica + " - " + enderecoClinica + " | Tel: " + telefoneClinica);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }
}

