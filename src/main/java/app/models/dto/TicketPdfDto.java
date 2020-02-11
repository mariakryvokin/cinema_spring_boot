package app.models.dto;

import java.sql.Timestamp;

public class TicketPdfDto {

    private long Id;
    private long seat;
    private Timestamp purchaseTimestamp;
    private String eventName;
    private Timestamp airDate;
    private String auditoriumName;

    public TicketPdfDto() {
    }

    public TicketPdfDto(long id, long seat, Timestamp purchaseTimestamp, String eventName, Timestamp airDate, String auditoriumName) {
        Id = id;
        this.seat = seat;
        this.purchaseTimestamp = purchaseTimestamp;
        this.eventName = eventName;
        this.airDate = airDate;
        this.auditoriumName = auditoriumName;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getSeat() {
        return seat;
    }

    public void setSeat(long seat) {
        this.seat = seat;
    }

    public Timestamp getPurchaseTimestamp() {
        return purchaseTimestamp;
    }

    public void setPurchaseTimestamp(Timestamp purchaseTimestamp) {
        this.purchaseTimestamp = purchaseTimestamp;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Timestamp getAirDate() {
        return airDate;
    }

    public void setAirDate(Timestamp airDate) {
        this.airDate = airDate;
    }

    public String getAuditoriumName() {
        return auditoriumName;
    }

    public void setAuditoriumName(String auditoriumName) {
        this.auditoriumName = auditoriumName;
    }

}
