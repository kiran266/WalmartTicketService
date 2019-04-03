package ticket.service.util;

import java.time.Instant;

import ticket.model.Stage;
import ticket.model.enums.SeatHoldStatus;
import ticket.model.enums.SeatStatus;

public class TicketUnHolder implements Runnable {

	private Long numOfSecondsToHold;
	private Stage stage;

	public TicketUnHolder(Stage stage, Long numOfSecondsToHold) {
		this.stage = stage;
		this.numOfSecondsToHold = numOfSecondsToHold;
	}

	private void unHoldTickets() {
		stage.getHeldSeats()
			 .forEach((id, value) -> {
				 if (value.getStatus()
						  .equals(SeatHoldStatus.HOLDING) && value.getReserveDate()
																  .plusSeconds(numOfSecondsToHold)
																  .compareTo(Instant.now()) < 0) {
					 value.getSeatsHeld()
						  .forEach(seat -> seat.setStatus(SeatStatus.FREE));
					 value.setStatus(SeatHoldStatus.REMOVED);
				 }
			 });
	}

	@Override
	public void run() {
		unHoldTickets();
	}
}
