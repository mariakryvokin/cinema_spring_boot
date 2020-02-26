package app.config.viewresolvers;

import app.models.Ticket;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
public class PDFView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map map, Document document, PdfWriter pdfWriter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        Table table = new Table(4);
        table.addCell("Event name");
        table.addCell("Air time");
        table.addCell("Auditorium name");
        table.addCell("Seat number");

       List<Ticket> tickets = (List<Ticket>) map.get("ticketsToBeBought");

       if(tickets != null){
           tickets.forEach(t->{
               try {
                   table.addCell(t.getEventHasAuditorium().getEvent().getName());
                   table.addCell(t.getEventHasAuditorium().getAirDate().toString());
                   table.addCell(t.getEventHasAuditorium().getAuditorium().getName());
                   table.addCell(String.valueOf(t.getSeat()));
               } catch (BadElementException e) {
                   e.printStackTrace();
               }
           });
       }
        document.add(table);
    }
}
