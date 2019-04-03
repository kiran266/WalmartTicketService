package ticket.model.enums;

public enum SeatStatus {
    /**
     * Free status, available for holding
     */
    FREE,
    /**
     * Held status, cannot be held again but can be reserved and freed
     */
    HOLD,
    /**
     * Reserved status, once reserved cannot change state again
     */
    RESERVED
}
