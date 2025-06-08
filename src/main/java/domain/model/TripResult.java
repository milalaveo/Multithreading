package domain.model;

/**
 * Результат завершенной поездки.
 */
public class TripResult {
    private final Passenger passenger;
    private final Taxi taxi;
    private final long durationMillis;

    public TripResult(Passenger passenger, Taxi taxi, long durationMillis) {
        this.passenger = passenger;
        this.taxi = taxi;
        this.durationMillis = durationMillis;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public long getDurationMillis() {
        return durationMillis;
    }

    @Override
    public String toString() {
        return "TripResult{" +
                "passenger=" + passenger +
                ", taxi=" + taxi +
                ", durationMillis=" + durationMillis +
                '}';
    }
}
