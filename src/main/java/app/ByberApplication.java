package app;

import domain.model.*;
import service.dispatcher.OrderDispatcher;
import service.support.CoordinateGenerator;
import service.support.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Точка входа для симуляции Byber (многопоточного такси).
 */
public class ByberApplication {
    private static final Logger logger = LogManager.getLogger(ByberApplication.class);


    public static void main(String[] args) {

        // Параметры генерации координат (например, городская зона 0-100 по x и y)
        CoordinateGenerator coordinateGenerator = new CoordinateGenerator(0, 100, 0, 100);
        IdGenerator idGenerator = new IdGenerator();

        // Создаём диспетчер (Singleton)
        OrderDispatcher dispatcher = OrderDispatcher.getInstance();

        // Создаём такси
        List<Taxi> taxis = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Taxi taxi = new Taxi("Taxi-" + idGenerator.nextId(), coordinateGenerator.generate());
            taxis.add(taxi);
            dispatcher.registerTaxi(taxi);

            // Запускаем такси в отдельном потоке
            new Thread(taxi, taxi.getTaxiId()).start();
        }

        // Создаём пассажиров
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Passenger passenger = new Passenger(
                    "Passenger-" + idGenerator.nextId(),
                    coordinateGenerator.generate(),
                    coordinateGenerator.generate()
            );
            passengers.add(passenger);
        }

        // Эмуляция вызова такси пассажирами
        for (Passenger passenger : passengers) {
            Order order = new Order(passenger, passenger.getCurrentLocation(), passenger.getDestination());
            dispatcher.dispatchOrder(order);
        }

        // Для упрощения: программа не закрывается сразу (в реальном проекте - shutdown логика)
        try {
            TimeUnit.MILLISECONDS.sleep(10000); // Даем такси время поработать
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        logger.info("Simulation finished.");
        System.exit(0);
    }
}
