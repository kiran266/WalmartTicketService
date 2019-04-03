package ticket.model;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import lombok.Data;
import ticket.model.enums.SeatStatus;

@Data
public class Stage {

	private static Stage instance;
	private Seat[][] stageSeats;
	private ConcurrentHashMap<Integer, SeatHold> heldSeats = new ConcurrentHashMap<>();

	private int cols, rows;

	private Stage(int rows, int cols) {
		this.cols = cols;
		this.rows = rows;
		stageSeats = new Seat[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				stageSeats[i][j] = Seat.builder()
									   .row(i)
									   .col(j)
									   .status(SeatStatus.FREE)
									   .build();
			}
		}
	}

	public static void init(int rows, int cols) {
		instance = new Stage(rows, cols);
	}

	public static Stage getInstance() {
		Optional<Stage> optionalStage = Optional.ofNullable(instance);
		if (!optionalStage.isPresent())
			instance = new Stage(9, 34);
		return instance;
	}

	public Stream<Seat> getAllSeatByStream() {
		return Arrays.stream(stageSeats)
					 .flatMap(Arrays::stream);
	}
}
