package domain.model;

import domain.contract.TaxiWorker;
import domain.contract.TaxiOperations;
import domain.model.state.TaxiContext;
import domain.model.state.TaxiState;
import shared.TripException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Taxi implements TaxiWorker, TaxiOperations {
    private static final Logger logger = LogManager.getLogger(Taxi.class);

    private final String id;
    private Coordinate currentLocation;

    private final Queue<Order> orders = new LinkedList<>();

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();

    private final TaxiContext stateContext;

    public Taxi(String id, Coordinate startLocation) {
        this.id = id;
        this.currentLocation = startLocation;
        this.stateContext = new TaxiContext(this);
    }

    @Override
    public String getTaxiId() {
        return id;
    }

    @Override
    public void run() {
        logger.info("Taxi " + id + " started at " + currentLocation);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order;

                lock.lock();
                try {
                    while (orders.isEmpty()) {
                        notEmpty.await();
                    }
                    order = orders.poll();
                } finally {
                    lock.unlock();
                }

                logger.info("Taxi " + id + " accepted order: " + order);

                try {
                    stateContext.handleOrder(order);
                    logger.info("Taxi " + id + " completed order for " + order.getPassenger());
                } catch (TripException e) {
                    logger.error("Taxi " + id + " encountered trip error: " + e.getMessage());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.info("Taxi " + id + " interrupted and stopping.");
        }
    }


    @Override
    public void receiveOrder(Order order) {
        lock.lock();
        try {
            orders.offer(order);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isAvailable() {
        // Состояние - доступно
        return stateContext.getState().getStateName().equals("Free");
    }

    public Coordinate getCurrentLocation() {
        lock.lock();
        try {
            return currentLocation;
        } finally {
            lock.unlock();
        }
    }

    public void setCurrentLocation(Coordinate currentLocation) {
        lock.lock();
        try {
            this.currentLocation = currentLocation;
        } finally {
            lock.unlock();
        }
    }
}
