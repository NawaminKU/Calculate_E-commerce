package Observer_Pattern;

import Data_Models.Order;

public interface OrderObserver {
    void update(Order order);
}
