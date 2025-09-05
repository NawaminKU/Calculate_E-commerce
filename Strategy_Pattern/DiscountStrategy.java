package Strategy_Pattern;

import Data_Models.Order;

public interface DiscountStrategy {
    double applyDiscount(Order order);
}
