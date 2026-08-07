package med.voll.api.infra.receita;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class FooterEvent extends PdfPageEventHelper {
    private final String footerText;

    public FooterEvent(String footerText) {
        this.footerText = footerText;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(523);
        table.setLockedWidth(true);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(new Phrase(footerText, new Font(Font.FontFamily.HELVETICA, 10)));

        // posição no rodapé
        table.writeSelectedRows(0, -1,
                (document.right() - document.left() - table.getTotalWidth()) / 2 + document.leftMargin(),
                document.bottom() - 10, // distância da margem inferior
                writer.getDirectContent());
    }
}
