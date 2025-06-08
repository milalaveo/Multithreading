package domain.contract;

import domain.model.Order;
import domain.model.Taxi;

import java.util.List;

public interface Dispatcher {
    void registerTaxi(Taxi taxi);
    void dispatchOrder(Order order);
    List<Taxi> getAvailableTaxis();
}
