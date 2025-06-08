package domain.model.state;

import domain.model.Order;
import shared.TripException;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DrivingToDestinationState implements TaxiState {
    private static final Logger logger = LogManager.getLogger(DrivingToDestinationState.class);


    @Override
    public void handleOrder(TaxiContext context, Order order) throws InterruptedException, TripException {
        logger.info("Driving to destination.");

        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new TripException("Trip interrupted", e);
        }

        context.setState(new FreeState());
    }

    @Override
    public String getStateName() {
        return "Driving to Destination";
    }
}
