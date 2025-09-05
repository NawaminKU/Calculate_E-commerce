package Strategy_Pattern;

import Data_Models.Order;

public class PrecentageDiscount implements DiscountStrategy {
    private final double percentage;

    public PrecentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(Order order) {
        return order.getTotalPrice() * (1.0 - percentage / 100.0);
    }
    
}
