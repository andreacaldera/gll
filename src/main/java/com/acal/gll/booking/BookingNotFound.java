package com.acal.gll.booking;

/**
 * Created by andrea on 27/05/2014.
 */
public class BookingNotFound extends RuntimeException {

    public BookingNotFound(String message) {
        super(message);
    }

    public BookingNotFound(Exception exception) {
        super(exception);
    }
}
