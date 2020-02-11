package app.controllers.messageconverters;

import app.models.dto.TicketPdfDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class TicketPdfConverter implements HttpMessageConverter<TicketPdfDto>{

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return clazz.equals(TicketPdfDto.class);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Arrays.asList(MediaType.APPLICATION_PDF);
    }

    @Override
    public TicketPdfDto read(Class<? extends TicketPdfDto> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(TicketPdfDto ticketPdfDto, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        Document document = new Document();
        try {
            OutputStream outputStream = outputMessage.getBody();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            PdfPTable table = new PdfPTable(6);
            table.addCell("Ticket id");
            table.addCell("Purchase time");
            table.addCell("Event name");
            table.addCell("Air date");
            table.addCell("Auditorium name");
            table.addCell("Seat");
            table.addCell(String.valueOf(ticketPdfDto.getId()));
            table.addCell(ticketPdfDto.getPurchaseTimestamp().toString());
            table.addCell(ticketPdfDto.getEventName());
            table.addCell(ticketPdfDto.getAirDate().toString());
            table.addCell(ticketPdfDto.getAuditoriumName());
            table.addCell(String.valueOf(ticketPdfDto.getSeat()));
            document.add(table);
            document.close();
            outputStream.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
