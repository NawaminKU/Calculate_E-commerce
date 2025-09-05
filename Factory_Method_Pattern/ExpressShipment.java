package Factory_Method_Pattern;

public class ExpressShipment implements Shipment {

    @Override
    public String getInfo() {
        return "Express Delivery";
    }

    @Override
    public double getCost() {
        return 150.0;
    }
    
}
