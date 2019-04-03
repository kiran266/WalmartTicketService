package ticket.model.enums;

public enum SeatHoldStatus {
    /**
     * Successfully Reserved state
     */
    SUCCESS,
    /**
     * Failed state, was unable to book any seat due to lack of seats to be booked
     */
    FAILED,
    /**
     * Removed state, Expired bookings ie didn't get reserved before the expiration
     */
    REMOVED,
    /**
     * State of being either removed or being reserved
     */
    HOLDING,
    /**
     * State when the input data has error in it
     */
    ERROR
}
