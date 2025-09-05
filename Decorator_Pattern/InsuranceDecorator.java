package Decorator_Pattern;

import Data_Models.Order;
import Factory_Method_Pattern.Shipment;

public class InsuranceDecorator extends ShipmentDecorator {
    private final Order order;

    public InsuranceDecorator(Shipment wrappedShipment, Order order) {
        super(wrappedShipment);
        this.order = order;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " + Insurance";
    }

    @Override
    public double getCost() {
        return super.getCost() + (order.getTotalPrice() * 0.10);
    }
    
}
