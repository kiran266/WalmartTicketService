package ticket.model;

import lombok.Data;
import ticket.model.enums.SeatHoldStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class SeatHold {
    private int seatHeldId;
    private List<Seat> seatsHeld = new ArrayList<>();
    private String customerEmail;
    private Instant reserveDate = Instant.now();
    private SeatHoldStatus status;

    public SeatHold(int seatHeldId, String customerEmail, SeatHoldStatus status) {
        this.seatHeldId = seatHeldId;
        this.customerEmail = customerEmail;
        this.status = status;
    }

    public SeatHold(SeatHoldStatus status) {
        this.status = status;
    }

}
