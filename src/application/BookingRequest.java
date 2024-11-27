package application;

public class BookingRequest {
    private int bookingID;
    private String roomType;
    private int quantity;
    private String location;

    public BookingRequest(int bookingID, String roomType, int quantity, String location) {
        this.bookingID = bookingID;
        this.roomType = roomType;
        this.quantity = quantity;
        this.location = location;
    }

    public int getBookingID() {
        return bookingID;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getLocation() {
        return location;
    }
}
