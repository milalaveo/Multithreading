package domain.contract;
import domain.model.Order;

public interface TaxiOperations {
    void receiveOrder(Order order);
    boolean isAvailable();
}
