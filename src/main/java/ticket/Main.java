package ticket;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import ticket.model.SeatHold;
import ticket.model.enums.SeatHoldStatus;
import ticket.service.TicketServiceImpl;
import ticket.service.util.SeatHelper;

public class Main {

	public static void main(String[] args) {
		TicketServiceImpl service = new TicketServiceImpl(9, 34, 20);
		List<SeatHold> heldSeats = new ArrayList<>();
		while (true) {
			System.out.println("================");
			System.out.println("Please choose from the choices below.");
			System.out.println("1. Show Free Seats.");
			System.out.println("2. Book Tickets.");
			System.out.println("3. Finalize Reservation.");
			System.out.println("4. Wait for 20 sec.");
			System.out.println("5. Exit.");
			System.out.println("==============\n");
			Scanner scanner = new Scanner(System.in);
			int input;
			try {
				input = scanner.nextInt();
				switch (input) {
				case (1): {
					int numSeatsAvailable = service.numSeatsAvailable();
					System.out.println("There are " + numSeatsAvailable + " available to be booked.");
					break;
				}
				case 2: {
					System.out.println("Please enter the number of seats you would like to reserve.");
					Scanner scanner1 = new Scanner(System.in);
					int numSeats;
					try {
						numSeats = scanner1.nextInt();
					} catch (InputMismatchException e) {
						System.out.println("Invalid input. Please enter valid integer number that is less or equal to available seats.");
						break;
					}
					System.out.println();
					System.out.println("Please enter the email id of the customer");
					String customerEmail;
					Scanner scanner2 = new Scanner(System.in);
					customerEmail = scanner2.nextLine();

					if (!SeatHelper.isValidMail(customerEmail)) {
						System.out.println("Please Enter valid email address.");
						break;
					}

					SeatHold seatHold = service.findAndHoldSeats(numSeats, customerEmail);

					if (seatHold.getStatus()
								.equals(SeatHoldStatus.HOLDING)) {
						System.out.println("Successfully held seats. Your Order Id is : " + seatHold.getSeatHeldId());
						heldSeats.add(seatHold);
					} else if (seatHold.getStatus()
									   .equals(SeatHoldStatus.ERROR))
						System.out.println("Invalid Data Entered. Try Again.");
					else
						System.out.println("Not enough free seats to book. Try again.");

					break;
				}
				case (3): {
					if (heldSeats.size() > 0) {
						System.out.println("Enter order id to book the reservation:");
						for (SeatHold heldSeat : heldSeats) {
							System.out.println("id = " + heldSeat.getSeatHeldId() + " - " + heldSeat);
							System.out.println();
						}
						Scanner scanner3 = new Scanner(System.in);
						int orderId;
						try {
							orderId = scanner3.nextInt();
							Optional<SeatHold> seatHold = heldSeats.stream()
																   .filter(e -> e.getSeatHeldId() == orderId)
																   .findFirst();
							if (seatHold.isPresent()) {
								System.out.println(service.reserveSeats(seatHold.get()
																				.getSeatHeldId(), seatHold.get()
																										  .getCustomerEmail()));
							} else
								throw new InputMismatchException();
						} catch (InputMismatchException e) {
							System.out.println("Please enter an valid Order Id from booking step.");
							break;
						}
						break;

					} else {
						System.out.println("No Reservation to book. First book then try again");
					}
					break;
				}
				case (4): {
					try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						Thread.currentThread()
							  .interrupt();
					}
					break;
				}
				case (5): {
					System.exit(0);
					break;
				}
				default: {
					System.out.println("Wrong input. Please try again between the given values.");
				}
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter an integer value within the range.");
			}

		}
	}
}
