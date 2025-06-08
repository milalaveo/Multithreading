package domain.model.state;

import domain.model.Order;
import shared.TripException;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoingToPassengerState implements TaxiState {
    private static final Logger logger = LogManager.getLogger(GoingToPassengerState.class);


    @Override
    public void handleOrder(TaxiContext context, Order order) throws InterruptedException, TripException {
        logger.info("Going to passenger.");
        context.setState(new DrivingToDestinationState());
        TimeUnit.MILLISECONDS.sleep(1000);  // эмуляция посадки
        context.handleOrder(order);  // переход к следующему состоянию
    }


    @Override
    public String getStateName() {
        return "Going to Passenger";
    }
}
