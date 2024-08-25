package ResponsePOJO;

import RequestPOJO.CreateBooking;

public class CreateBookingResponse {

    private int bookingid;
    private CreateBooking booking;

    // Default constructor
    public CreateBookingResponse() {
    }

    // Parameterized constructor
    public CreateBookingResponse(int bookingid, CreateBooking booking) {
        this.bookingid = bookingid;
        this.booking = booking;
    }

    // Getters and setters
    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public CreateBooking getBooking() {
        return booking;
    }

    public void setBooking(CreateBooking booking) {
        this.booking = booking;
    }
}
