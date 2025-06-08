package shared;

public class TripException extends Exception {
    public TripException(String message) {
        super(message);
    }

    public TripException(String message, Throwable cause) {
        super(message, cause);
    }
}
