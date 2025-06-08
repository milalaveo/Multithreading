package service.dispatcher;

import domain.contract.Dispatcher;
import domain.model.Order;
import domain.model.Taxi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import service.support.DistanceCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Singleton-диспетчер заказов
 * Отвечает за регистрацию такси и распределение заказов.
 */
public class OrderDispatcher implements Dispatcher {
    private static final Logger logger = LogManager.getLogger(OrderDispatcher.class);

    private final List<Taxi> taxis = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    private static OrderDispatcher instance;

    private OrderDispatcher() {
        // private constructor для singleton
    }

    public static OrderDispatcher getInstance() {
        if (instance == null) {
            synchronized (OrderDispatcher.class) {
                if (instance == null) {
                    instance = new OrderDispatcher();
                }
            }
        }
        return instance;
    }

    @Override
    public void registerTaxi(Taxi taxi) {
        lock.lock();
        try {
            taxis.add(taxi);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void dispatchOrder(Order order) {
        Taxi bestTaxi = null;
        double bestDistance = Double.MAX_VALUE;

        lock.lock();
        try {
            for (Taxi taxi : taxis) {
                if (taxi.isAvailable()) {
                    double distance = DistanceCalculator.calculate(taxi.getCurrentLocation(), order.getPickupLocation());
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        bestTaxi = taxi;
                    }
                }
            }

            if (bestTaxi != null) {
                bestTaxi.receiveOrder(order);
                logger.info("Order dispatched to Taxi " + bestTaxi.getTaxiId());
            } else {
                logger.info("No available taxis for order: " + order);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<Taxi> getAvailableTaxis() {
        lock.lock();
        try {
            List<Taxi> available = new ArrayList<>();
            for (Taxi taxi : taxis) {
                if (taxi.isAvailable()) {
                    available.add(taxi);
                }
            }
            return Collections.unmodifiableList(available);
        } finally {
            lock.unlock();
        }
    }
}
