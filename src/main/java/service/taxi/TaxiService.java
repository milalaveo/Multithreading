package service.taxi;

import domain.contract.TaxiOperations;
import domain.model.Order;
import domain.model.Taxi;

import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Сервис управления такси: обработка заказов, обновление статуса, локации.
 */
public class TaxiService implements TaxiOperations {
    private static final Logger logger = LogManager.getLogger(TaxiService.class);


    private final Taxi taxi;
    private final ReentrantLock lock = new ReentrantLock();

    public TaxiService(Taxi taxi) {
        this.taxi = taxi;
    }

    @Override
    public void receiveOrder(Order order) {
        lock.lock();
        try {
            if (taxi.isAvailable()) {
                taxi.receiveOrder(order);
                logger.info("TaxiService: Order accepted by Taxi " + taxi.getTaxiId());
            } else {
                logger.info("TaxiService: Taxi " + taxi.getTaxiId() + " is busy.");
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isAvailable() {
        lock.lock();
        try {
            return taxi.isAvailable();
        } finally {
            lock.unlock();
        }
    }

    public Taxi getTaxi() {
        return taxi;
    }
}
