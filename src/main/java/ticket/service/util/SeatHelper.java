package ticket.service.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ticket.model.Seat;
import ticket.model.Stage;
import ticket.model.enums.SeatStatus;

public class SeatHelper {

	public static List<Seat> getBestRow(Stage stage, int numberOfSeats) {
		List<Seat> returnSeats = new ArrayList<>();
		for (int i = 0; i < stage.getRows(); i++) {
			returnSeats.clear();
			for (int j = 0; j < stage.getCols(); j++) {
				if (!stage.getStageSeats()[i][j].getStatus()
												.equals(SeatStatus.FREE)) {
					returnSeats.clear();
					continue;
				}
				returnSeats.add(stage.getStageSeats()[i][j]);
				if (returnSeats.size() == numberOfSeats) {
					return returnSeats;
				}
			}
		}
		// this will run when numofseats are more than one row or available seats are lesser than requested in a single row.
		return stage.getAllSeatByStream()
					.filter(seat -> seat.getStatus()
										.equals(SeatStatus.FREE))
					.limit(numberOfSeats)
					.collect(Collectors.toList());
	}

	public static boolean isValidMail(String email) {
		Optional<String> optional = Optional.ofNullable(email);
		if (optional.isPresent()) {
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
					"[a-zA-Z0-9_+&*-]+)*@" +
					"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
					"A-Z]{2,7}$";
			Pattern pat = Pattern.compile(emailRegex);
			return pat.matcher(email)
					  .matches();
		} else
			return false;
	}

}
