package Strategy_Pattern;

import Data_Models.Order;

public class OrderCalculator {
    public double calculatorFinalPrice(Order order, DiscountStrategy strategy) {
        return strategy.applyDiscount(order);
    }
}
