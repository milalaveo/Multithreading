package domain.model.state;

import domain.model.Order;
import shared.TripException;

public interface TaxiState {
    void handleOrder(TaxiContext context, Order order) throws InterruptedException, TripException;
    String getStateName();
}
