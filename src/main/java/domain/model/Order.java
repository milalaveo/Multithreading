package domain.model;

import java.time.Instant;

public class Order {
    private final Passenger passenger;
    private final Coordinate pickupLocation;
    private final Coordinate destination;
    private final Instant createdAt;

    public Order(Passenger passenger, Coordinate pickupLocation, Coordinate destination) {
        this.passenger = passenger;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.createdAt = Instant.now();
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Coordinate getPickupLocation() {
        return pickupLocation;
    }

    public Coordinate getDestination() {
        return destination;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "passenger=" + passenger +
                ", pickup=" + pickupLocation +
                ", destination=" + destination +
                ", createdAt=" + createdAt +
                '}';
    }
}
