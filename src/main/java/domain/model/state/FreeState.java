package domain.model.state;

import domain.model.Order;
import shared.TripException;


import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FreeState implements TaxiState {
    private static final Logger logger = LogManager.getLogger(FreeState.class);


    @Override
    public void handleOrder(TaxiContext context, Order order) throws InterruptedException, TripException {
        logger.info("Taxi is free. Accepting order.");
        context.setState(new GoingToPassengerState());
        TimeUnit.MILLISECONDS.sleep(1000);  // эмуляция времени поездки к пассажиру
        context.handleOrder(order);  // переход к следующему состоянию
    }

    @Override
    public String getStateName() {
        return "Free";
    }
}
