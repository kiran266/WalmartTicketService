import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ticket.constants.ResponseConstants;
import ticket.model.SeatHold;
import ticket.model.enums.SeatHoldStatus;
import ticket.service.TicketServiceImpl;
import ticket.service.util.SeatHelper;

class SimpleTest {

	private TicketServiceImpl service;
	private String mail = "test@test.com";

	@BeforeEach
	void init() {
		int row = 5;
		int col = 5;
		int hold = 1;
		service = new TicketServiceImpl(row, col, hold);
	}

	@Test
	void holdingTest() {
		SeatHold seatHold = service.findAndHoldSeats(2, mail);
		assertNotNull(seatHold);
		assertEquals(mail, seatHold.getCustomerEmail());
		assertEquals(SeatHoldStatus.HOLDING, seatHold.getStatus());
		assertEquals(2, seatHold.getSeatsHeld()
								.size());
	}

	@Test
	void invalidData() {
		SeatHold invalidNoOfSeat = service.findAndHoldSeats(0, mail);
		assertNotNull(invalidNoOfSeat);
		assertEquals(SeatHoldStatus.ERROR, invalidNoOfSeat.getStatus());
		SeatHold invalidEmail = service.findAndHoldSeats(1, null);
		assertNotNull(invalidEmail);
		assertEquals(SeatHoldStatus.ERROR, invalidEmail.getStatus());
	}

	@Test
	void reservationTest() {
		SeatHold seatHold = service.findAndHoldSeats(2, mail);
		assertNotNull(seatHold);
		assertEquals(SeatHoldStatus.HOLDING, seatHold.getStatus());
		String result = service.reserveSeats(seatHold.getSeatHeldId(), seatHold.getCustomerEmail());
		assertEquals(SeatHoldStatus.SUCCESS, seatHold.getStatus());
		assertNotNull(result);
		result = service.reserveSeats(seatHold.getSeatHeldId(), "");
		assertEquals(ResponseConstants.EMAIL_MISMATCH, result);
		result = service.reserveSeats(seatHold.getSeatHeldId(), seatHold.getCustomerEmail());
		assertEquals(ResponseConstants.ALREADY_APPROVED, result);
		result = service.reserveSeats(-1, seatHold.getCustomerEmail());
		assertEquals(ResponseConstants.INVALID_ORDER_ID, result);
	}

	@Test
	void miscTest() {
		assertTrue(SeatHelper.isValidMail(mail));
		assertFalse(SeatHelper.isValidMail("asd"));
	}
}
