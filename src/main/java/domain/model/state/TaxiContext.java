package domain.model.state;

import domain.model.Order;
import domain.model.Taxi;
import shared.TripException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaxiContext {
    private static final Logger logger = LogManager.getLogger(Taxi.class);

    private TaxiState currentState;
    private final Taxi taxi;

    public TaxiContext(Taxi taxi) {
        this.taxi = taxi;
        this.currentState = new FreeState();  // начальное состояние
    }

    public void setState(TaxiState state) {
        this.currentState = state;
        logger.info("Taxi " + taxi.getTaxiId() + " changed state to: " + state.getStateName());
    }

    public TaxiState getState() {
        return currentState;
    }

    public void handleOrder(Order order) throws InterruptedException, TripException {
        currentState.handleOrder(this, order);
    }


    public Taxi getTaxi() {
        return taxi;
    }
}
