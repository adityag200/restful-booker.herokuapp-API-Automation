package ResponsePOJO;

public class BookingDatesResponse {
    private String checkin;
    private String checkout;

    // Default constructor
    public BookingDatesResponse() {
    }

    // Parameterized constructor
    public BookingDatesResponse(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    // Getters and setters
    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }
}