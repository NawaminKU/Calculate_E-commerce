import Data_Models.*;
import Decorator_Pattern.*;
import Factory_Method_Pattern.*;
import Observer_Pattern.*;
import Strategy_Pattern.*;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("--- E-commerce System Simulation ---");

        // --- 1. Setup ---
        Product laptop = new Product("P001", "Laptop", 30000.0);
        Product mouse = new Product("P002", "Mouse", 800.0);
        Order myOder = new Order("ORD-001", List.of(laptop, mouse), "costumer@gmail.com");

        OrderCalculator calculator = new OrderCalculator();
        ShipmentFactory shipmentFactory = new ShipmentFactory();

        OrderProcessor orderProcessor = new OrderProcessor();
        InventoryService inventory = new InventoryService();
        EmailService emailer = new EmailService();
        orderProcessor.register(inventory);
        orderProcessor.register(emailer);

        System.out.println("\n--- 2. Testing Strategy Pattern (Discount) ---");
        double originalPrice = myOder.getTotalPrice();
        System.out.println("Original Price: " + originalPrice);

        DiscountStrategy tenPercentOff = new PrecentageDiscount(10);
        double priceAfterPercentage =calculator.calculatorFinalPrice(myOder, tenPercentOff);
        
        DiscountStrategy fiveHandredOff = new FixedDiscount(500);
        double priceAfterFixed = calculator.calculatorFinalPrice(myOder, fiveHandredOff);
        System.out.println("Price with 500 THB discount: " + priceAfterFixed);

        System.out.println("\n--- 3. Testing Factory and Decorator Patterns (Shipment) ---");
        // สร้าง Shipment แบบมาตรฐาน
        Shipment standardshiment = shipmentFactory.createShipment("STANDARD");
        System.out.println("Base shipment: " + standardshiment.getInfo() + ", Cost: " + standardshiment.getCost());
        // ห่อ ด้วยบริการห่อของขวัญ
        Shipment giftWrapped = new GiftWrapDecorator(standardshiment);
        System.out.println("Decorated: " + giftWrapped.getInfo() + ", Cost: " + giftWrapped.getCost());
        // ห่อ ทับด้วยบริการประกันสินค้า
        Shipment fullyLoaded = new InsuranceDecorator(giftWrapped, myOder);
        System.out.println("Fully Decorated: " + fullyLoaded.getInfo() + ", Cost: " + fullyLoaded.getCost());

        System.out.println("\n--- 4. Pricing Final Summary ---");
        double finalPrice = priceAfterPercentage;
        double totalCost = finalPrice + fullyLoaded.getCost();
        System.out.println("Final price after discount: " + finalPrice);
        System.out.println("Final shipment cost: " + fullyLoaded.getCost());
        System.out.println("Total amount to pay: " + totalCost);

        // --- 5. Testing Observer Pattern (Order Processing) ---
        orderProcessor.processOrder(myOder);
    }
}
