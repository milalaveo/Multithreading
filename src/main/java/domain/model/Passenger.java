package domain.model;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Пассажир вызывающий такси и ожидающий результат поездки.
 */
public class Passenger implements Callable<TripResult> {
    private static final Logger logger = LogManager.getLogger(Passenger.class);

    private final String id;
    private final Coordinate currentLocation;
    private final Coordinate destination;

    public Passenger(String id, Coordinate currentLocation, Coordinate destination) {
        this.id = id;
        this.currentLocation = currentLocation;
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public Coordinate getCurrentLocation() {
        return currentLocation;
    }

    public Coordinate getDestination() {
        return destination;
    }

    @Override
    public TripResult call() throws Exception {
        logger.info("Passenger " + id + " is calling taxi...");

        // Здесь должна быть логика вызова такси и ожидания результата
        // имитируем поездку с задержкой
        long startTime = System.currentTimeMillis();

        // TODO: взаимодействие с диспетчером и такси (ожидание завершения поездки)

        TimeUnit.MILLISECONDS.sleep(2000); // эмуляция времени поездки

        long duration = System.currentTimeMillis() - startTime;

        // Для демонстрации — создадим фиктивного такси
        Taxi taxi = new Taxi("DemoTaxi", currentLocation);

        logger.info("Passenger " + id + " completed trip.");

        return new TripResult(this, taxi, duration);
    }

    @Override
    public String toString() {
        return "Passenger{" + "id='" + id + '\'' + '}';
    }
}
